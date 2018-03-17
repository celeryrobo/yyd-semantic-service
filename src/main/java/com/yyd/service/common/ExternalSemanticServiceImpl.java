package com.yyd.service.common;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;
import com.yyd.external.semantic.ExternalSemanticResult;
import com.yyd.external.semantic.ExternalSemanticService;

public class ExternalSemanticServiceImpl implements Semantic<CommonBean> {
	private static final Logger LOG = Logger.getLogger(ExternalSemanticServiceImpl.class.getSimpleName());
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
			LOG.info("**********************************");
			LOG.info("Ret : " + semanticResult.getRet());
			LOG.info("Answer : " + semanticResult.getAnswer());
			LOG.info("Service : " + semanticResult.getService());
			LOG.info("Intent : " + semanticResult.getIntent());
			LOG.info("Msg : " + semanticResult.getMsg());
			LOG.info("Src Result : " + semanticResult.getSrcResult());
			bean = buildCommonBean(ybnfCompileResult, semanticResult);
		} catch (Exception e) {
			bean = new CommonBean();
			bean.setErrCode(500);
			bean.setErrMsg(e.getMessage());
		}
		return bean;
	}

	public CommonBean buildCommonBean(YbnfCompileResult ybnfCompileResult, ExternalSemanticResult semanticResult) {
		CommonBean bean = new CommonBean();
		bean.setErrCode(semanticResult.getRet());
		if (0 != semanticResult.getRet()) {
			return bean;
		}
		String service = semanticResult.getService();
		if (service != null) {
			ybnfCompileResult.setService(service);
		}
		Map<String, String> objects = ybnfCompileResult.getObjects();
		Map<String, Object> slots = semanticResult.getSlots();
		if (slots != null) {
			for (Entry<String, Object> entry : slots.entrySet()) {
				objects.put(entry.getKey(), entry.getValue().toString());
			}
		}
		Map<String, String> yydSlots = ybnfCompileResult.getSlots();
		String intent = semanticResult.getIntent();
		if (intent != null) {
			yydSlots.put("intent", intent);
		}
		bean.setText(semanticResult.getAnswer());
		return bean;
	}
}
