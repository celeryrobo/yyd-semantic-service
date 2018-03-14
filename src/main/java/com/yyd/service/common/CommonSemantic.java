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

	private CommonBean xunfeiSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String text = (String) semanticContext.getLocalVar();
		if (text == null) {
			return null;
		}
		ExternalSemanticResult semanticResult = new ExternalSemanticResult();
		semanticResult.setSrcResult(text);
		new XunfeiSemanticService().parseResult(text, semanticResult);
		CommonBean bean = new ExternalSemanticServiceImpl(null, null).buildCommonBean(ybnfCompileResult,
				semanticResult);
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	private CommonBean lingjuSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String userIp = "163.125.210.158";
		Map<String, String> params = new HashMap<>();
		// params.put("userId", "31936");// userId用户自定义即可
		// params.put("token", "2f38945bcb388ff135e1fc1d19505ddd");// 用userId绑定的
		params.put("userIp", userIp);// 目前应该是可以随便填的，没有和用户绑定
		// params.put("authCode", "f6d5305a06963ca8db532f010997e2d5"); //
		// 数量有限制，花钱才能增加，但目前似乎只有一个在用
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

	private CommonBean sanjiaoshouSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1000861");// userId用户自定义即可
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(new SanjiaoshouSemanticService(), params);
		CommonBean bean = semantic.handle(ybnfCompileResult, semanticContext);
		if (bean == null || bean.getErrCode() != 0) {
			return null;
		}
		return bean;
	}

	private CommonBean commonSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CommonBean bean = new CommonBean();
		bean.setErrCode(404);
		bean.setErrMsg("通用语义解析结构展示（缺少该场景解析逻辑）");
		bean.setText(ybnfCompileResult.toString());
		return bean;
	}
}
