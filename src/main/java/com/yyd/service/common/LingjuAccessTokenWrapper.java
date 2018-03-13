package com.yyd.service.common;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.mapper.LingjuAccessTokenMapper;
import com.yyd.service.domain.LingjuAccessToken;
import com.yyd.service.utils.CommonUtils;

@Component
public class LingjuAccessTokenWrapper {
	private static final String ACCESS_TOKEN_KEY = "LINGJU:ACCESS:TOKEN";
	@Autowired
	private LingjuAccessTokenMapper accessTokenMapper;
	@Autowired
	private StringRedisTemplate redisTemplate;
	private Map<Object, Object> redisMap = null;

	@PostConstruct
	public void init() {
		redisMap = new RedisStringMap(redisTemplate, ACCESS_TOKEN_KEY);
		List<LingjuAccessToken> accessTokens = accessTokenMapper.findAll();
		for (int i = 0; i < accessTokens.size(); i++) {
			LingjuAccessToken accessToken = accessTokens.get(i);
			StringBuilder sb = new StringBuilder();
			sb.append(accessToken.getUserId()).append(":").append(accessToken.getAccessToken());
			StringBuilder key = new StringBuilder("TOKEN:").append(i);
			redisMap.put(key.toString(), sb.toString());
		}
	}

	public void wrapper(SemanticContext semanticContext, Map<String, String> params) {
		String token = (String) semanticContext.getAttrs().get("token");
		if (token == null) {
			Integer accessCount = accessTokenMapper.count();
			int index = CommonUtils.randomInt(accessCount);
			StringBuilder key = new StringBuilder("TOKEN:").append(index);
			token = (String) redisMap.get(key.toString());
		}
		if (token != null && -1 != token.indexOf(':')) {
			semanticContext.getAttrs().put("token", token);
			String[] arr = token.split(":");
			params.put("userId", arr[0]);
			params.put("token", arr[1]);
		}
	}
}
