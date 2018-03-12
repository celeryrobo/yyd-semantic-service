package com.yyd.service.common;

import java.util.Map;
import java.util.Map.Entry;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.external.semantic.ExternalSemanticResult;
import com.yyd.external.semantic.ExternalSemanticService;

public class ExternalSemanticServiceImpl implements Semantic<CommonBean> {
	private ExternalSemanticService service;
	private Map<String, String> params;

	public ExternalSemanticServiceImpl(ExternalSemanticService service, Map<String, String> params) {
		this.service = service;
		this.params = params;
	}

	@Override
	public CommonBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CommonBean bean = null;
		try {
			ExternalSemanticResult semanticResult = service.handleSemantic(ybnfCompileResult.getText(), params);
			Map<String, String> objects = ybnfCompileResult.getObjects();
			for (Entry<String, Object> entry : semanticResult.getSlots().entrySet()) {
				objects.put(entry.getKey(), entry.getValue().toString());
			}
			Map<String, String> slots = ybnfCompileResult.getSlots();
			slots.put("intent", semanticResult.getIntent());
			bean = new CommonBean();
			bean.setSemantic(semanticResult.getAnswer());
		} catch (Exception e) {
			bean = new CommonBean();
			bean.setErrCode(500);
			bean.setErrMsg(e.getMessage());
		}
		return bean;
	}
}
