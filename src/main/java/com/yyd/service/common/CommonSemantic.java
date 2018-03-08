package com.yyd.service.common;

import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

@Service("common")
public class CommonSemantic implements Semantic<CommonBean> {
	@Override
	public CommonBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		CommonBean bean = new CommonBean();
		bean.setErrCode(404);
		bean.setErrMsg("通用语义解析结构展示（缺少该场景解析逻辑）");
		bean.setSemantic(ybnfCompileResult.toString());
		return bean;
	}
}
