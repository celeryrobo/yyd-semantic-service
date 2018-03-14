package com.yyd.service.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticContext;
import com.yyd.external.semantic.ExternalSemanticResult;
import com.yyd.external.semantic.lingju.LingjuSemanticService;
import com.yyd.service.common.mapper.LingjuAccessTokenMapper;
import com.yyd.service.domain.LingjuAccessToken;

@Component
public class LingjuAccessTokenWrapper {
	private static final Long TIMEOUT = 24L * 60L * 60L;
	private static final String ACCESS_TOKEN_KEY = "LINGJU:ACCESS:TOKEN";
	public static final int ACCESS_TOKEN_LIMIT = 200;
	public static final int ACCESS_TOKEN_START = 10000;
	public static int MAX_USER_ID = ACCESS_TOKEN_START;
	@Autowired
	private LingjuAccessTokenMapper accessTokenMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	private Map<Object, Object> readyMap = null;

	@PostConstruct
	public void init() {
		readyMap = new RedisStringMap(redisTemplate, ACCESS_TOKEN_KEY);
		List<LingjuAccessToken> accessTokens = accessTokenMapper.findAll();
		for (LingjuAccessToken accessToken : accessTokens) {
			StringBuilder value = new StringBuilder();
			value.append(accessToken.getUserId()).append(":").append(accessToken.getAccessToken());
			StringBuilder key = new StringBuilder("TOKEN:").append(accessToken.getUserId());
			readyMap.put(key.toString(), value.toString());
			Integer currUserId = Integer.valueOf(accessToken.getUserId());
			MAX_USER_ID = MAX_USER_ID > currUserId ? MAX_USER_ID : currUserId;
		}
		recycle();
	}

	public void wrapper(SemanticContext semanticContext, Map<String, String> params) {
		String token = (String) semanticContext.getAttrs().get("token");
		if (token == null) {
			if (readyMap.isEmpty()) {
				synchronized (this) {
					if (readyMap.isEmpty()) {
						recycle();
					}
					if (readyMap.isEmpty()) {
						init();
					}
				}
			}
			String tokenKey = null;
			for (Object key : readyMap.keySet()) {
				tokenKey = (String) key;
				break;
			}
			if (tokenKey != null) {
				synchronized (this) {
					token = (String) readyMap.get(tokenKey);
					readyMap.remove(tokenKey);
				}
			}
		}
		if (token != null && -1 != token.indexOf(':')) {
			semanticContext.getAttrs().put("token", token);
			String[] arr = token.split(":");
			params.put("userId", arr[0]);
			params.put("token", arr[1]);
		}
	}

	private void recycle() {
		Set<String> keys = redisTemplate.keys("SEMANTIC:CONTEXT:ATTRS:USER:*");
		for (String key : keys) {
			Long seconds = redisTemplate.getExpire(key, TimeUnit.SECONDS);
			seconds = seconds > 0 ? seconds : 0;
			Map<Object, Object> map = new RedisStringMap(redisTemplate, key);
			if ((TIMEOUT - seconds) >= 2L * 60L * 60L) {
				recycle(map);
			} else {
				String token = (String) map.get("token");
				int index = -1;
				if (token != null && -1 != (index = token.indexOf(':')) && index > 0) {
					String tokenKey = "TOKEN:" + token.substring(0, index);
					if (readyMap.containsKey(tokenKey)) {
						readyMap.remove(tokenKey);
					}
				}
			}
		}
	}

	private void recycle(Map<Object, Object> map) {
		String token = (String) map.get("token");
		if (token != null && -1 != token.indexOf(':')) {
			StringBuilder key = new StringBuilder("TOKEN:").append(token.split(":")[0]);
			readyMap.put(key.toString(), token);
			map.remove("token");
		}
	}

	public String reloadAccessToken(LingjuSemanticService lingjuSemanticService, String userId, String userIp) {
		ExternalSemanticResult externalSemanticResult = new ExternalSemanticResult();
		String token = lingjuSemanticService.getAccessToken(userId, userIp, externalSemanticResult);
		System.out.println(userId + " lingju access token:" + token);
		System.out.println("lingju access token err msg : " + externalSemanticResult.getRet() + "/"
				+ externalSemanticResult.getMsg() + "/" + externalSemanticResult.getSemanticRet() + "/"
				+ externalSemanticResult.getSemanticMsg());
		LingjuAccessToken accessToken = accessTokenMapper.getByUserId(userId);
		if (accessToken == null) {
			accessToken = new LingjuAccessToken();
			accessToken.setUserId(userId);
			accessToken.setAccessToken(token);
			accessTokenMapper.add(accessToken);
		} else {
			accessToken.setAccessToken(token);
			accessTokenMapper.update(accessToken);
		}
		return token;
	}
}
