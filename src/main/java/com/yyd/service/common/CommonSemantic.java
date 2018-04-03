package com.yyd.service.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.external.semantic.ExternalSemanticResult;
import com.yyd.external.semantic.lingju.LingjuSemanticService;
import com.yyd.external.semantic.sanjiaoshou.SanjiaoshouSemanticService;
import com.yyd.external.semantic.turing.TuringSemanticService;
import com.yyd.external.semantic.turing.TuringUserIdService;
import com.yyd.external.semantic.xunfei.XunfeiSemanticService;

@Service("common")
public class CommonSemantic implements Semantic<CommonBean> {
	@Autowired
	private LingjuAccessTokenWrapper lingjuAccessTokenWrapper;

	@Override
	public CommonBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		semanticContext.setService("");
		CommonBean bean = xunfeiSemanticHandle(ybnfCompileResult, semanticContext);
		if (bean == null) {
			bean = tulingSemanticHandle(ybnfCompileResult, semanticContext);
		}
		if (bean == null) {
			bean = lingjuSemanticHandle(ybnfCompileResult, semanticContext);
		}
		if (bean == null) {
			bean = sanjiaoshouSemanticHandle(ybnfCompileResult, semanticContext);
		}
		if (bean == null) {
			bean = commonSemanticHandle(ybnfCompileResult, semanticContext);
		}
		return bean;
	}

	/**
	 * 讯飞语义解析方法，讯飞语义数据是由调用方传递过来，然后由该方法进行解析处理
	 * 
	 * @param ybnfCompileResult
	 *            语义处理结果
	 * @param semanticContext
	 *            用户语义上下文
	 * @return
	 */
	private CommonBean xunfeiSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String text = (String) semanticContext.getLocalVar();
		if (text == null) {
			return null;
		}
		// 客户端获取的讯飞语义不完成，需要补充完整后再进行解析
		text = new StringBuilder(
				"{\"code\":\"00000\",\"desc\":\"成功\",\"sid\":\"rwa2ac04d1c@chfca30da12150000100\",\"data\":")
						.append(text).append("}").toString();
		System.out.println("Xunfei Semantic Data : " + text);
		ExternalSemanticResult semanticResult = new ExternalSemanticResult();
		semanticResult.setSrcResult(text);
		CommonBean bean = null;
		try {
			new XunfeiSemanticService().parseResult(text, semanticResult);
			bean = new ExternalSemanticServiceImpl(null, null).buildCommonBean(ybnfCompileResult, semanticResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	/**
	 * 图灵语义处理方法
	 * 
	 * @param ybnfCompileResult
	 *            语义处理结果
	 * @param semanticContext
	 *            用户语义上下文
	 * @return
	 */
	private CommonBean tulingSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String key = "62ee2709d9b5484998875ef6ecf84b11";
		String secret = "CpsJ11V9SGE9rbJ1";
		final String TULING_TOKEN = "TULING:TOKEN";
		String userId = (String) semanticContext.getAttrs().get(TULING_TOKEN);
		if (userId == null) {
			userId = "100001"; // semanticContext.getUserIdentify();
			userId = TuringUserIdService.getTuringUserId(key, secret, userId, false);
			if (userId == null) {
				return null;
			}
			semanticContext.getAttrs().put(TULING_TOKEN, userId);
		}
		Map<String, String> params = new HashMap<>();
		params.put("key", key);
		params.put("secret", secret);
		params.put("userId", userId);
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(new TuringSemanticService(), params);
		CommonBean bean = semantic.handle(ybnfCompileResult, semanticContext);
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	/**
	 * 灵聚语义处理方法
	 * 
	 * @param ybnfCompileResult
	 *            语义处理结果
	 * @param semanticContext
	 *            用户语义上下文
	 * @return
	 */
	private CommonBean lingjuSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String userIp = "163.125.210.158";
		Map<String, String> params = new HashMap<>();
		// params.put("userId", "31936");// userId用户自定义即可
		// params.put("token", "2f38945bcb388ff135e1fc1d19505ddd");// 用userId绑定的
		params.put("userIp", userIp);// 目前应该是可以随便填的，没有和用户绑定
		// 数量有限制，花钱才能增加，但目前似乎只有一个在用
		// params.put("authCode", "f6d5305a06963ca8db532f010997e2d5");
		params.put("authCode", "a4fda24e3ff169df6393c946dbda4782");
		params.put("appKey", "dff8d355a221cf981cb646398a39eb37"); // 每个app唯一
		lingjuAccessTokenWrapper.wrapper(semanticContext, params);
		LingjuSemanticService lingjuSemanticService = new LingjuSemanticService();
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(lingjuSemanticService, params);
		CommonBean bean = semantic.handle(ybnfCompileResult, semanticContext);
		if (bean != null && (bean.getErrCode() == 1 || bean.getErrCode() == 7)) {
			String userId = params.get("userId");
			if (userId == null) {
				int max_user_id = ++LingjuAccessTokenWrapper.MAX_USER_ID;
				if (max_user_id >= (LingjuAccessTokenWrapper.ACCESS_TOKEN_START
						+ LingjuAccessTokenWrapper.ACCESS_TOKEN_LIMIT)) {
					// access token数量申请完，无法再次申请
					return null;
				}
				userId = "" + max_user_id;
				params.put("userId", userId);
			}
			String token = lingjuAccessTokenWrapper.reloadAccessToken(lingjuSemanticService, userId, userIp);
			if (token == null) {
				return null;
			}
			params.put("token", token);
			semanticContext.getAttrs().put("token", userId + ":" + token);
		}
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	/**
	 * 三角兽语义处理方法
	 * 
	 * @param ybnfCompileResult
	 *            语义处理结果
	 * @param semanticContext
	 *            用户语义上下文
	 * @return
	 */
	private CommonBean sanjiaoshouSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String userIdentify = semanticContext.getUserIdentify();
		Map<String, String> params = new HashMap<>();
		params.put("userId", userIdentify);// userId用户自定义即可
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(new SanjiaoshouSemanticService(), params);
		CommonBean bean = semantic.handle(ybnfCompileResult, semanticContext);
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	/**
	 * 通用兜底语义处理方法
	 * 
	 * @param ybnfCompileResult
	 *            语义处理结果
	 * @param semanticContext
	 *            用户语义上下文
	 * @return
	 */
	private CommonBean commonSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CommonBean bean = new CommonBean();
		bean.setErrCode(404);
		bean.setErrMsg("不知道你说的什么！");
		bean.setText(ybnfCompileResult.toString());
		return bean;
	}
}
