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
		return new ExternalSemanticServiceImpl(null, null).buildCommonBean(ybnfCompileResult, semanticResult);
	}

	private CommonBean lingjuSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> params = new HashMap<>();
		// params.put("userId", "31936");// userId用户自定义即可
		// params.put("token", "2f38945bcb388ff135e1fc1d19505ddd");// 用userId绑定的
		params.put("userIp", "163.125.210.158");// 目前应该是可以随便填的，没有和用户绑定
		params.put("authCode", "f6d5305a06963ca8db532f010997e2d5"); // 数量有限制，花钱才能增加，但目前似乎只有一个在用
		params.put("appKey", "dff8d355a221cf981cb646398a39eb37"); // 每个app唯一
		lingjuAccessTokenWrapper.wrapper(semanticContext, params);
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(new LingjuSemanticService(), params);
		return semantic.handle(ybnfCompileResult, semanticContext);
	}

	private CommonBean sanjiaoshouSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> params = new HashMap<>();
		params.put("userId", "1000861");// userId用户自定义即可
		Semantic<CommonBean> semantic = new ExternalSemanticServiceImpl(new SanjiaoshouSemanticService(), params);
		return semantic.handle(ybnfCompileResult, semanticContext);
	}

	private CommonBean commonSemanticHandle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CommonBean bean = new CommonBean();
		bean.setErrCode(404);
		bean.setErrMsg("通用语义解析结构展示（缺少该场景解析逻辑）");
		bean.setSemantic(ybnfCompileResult.toString());
		return bean;
	}
}
