package com.yyd.service.common;

import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

@Service("common")
public class CommonSemantic implements Semantic<CommonBean> {
	@Override
	public CommonBean handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return new CommonBean(ybnfCompileResult.toString());
	}
}
