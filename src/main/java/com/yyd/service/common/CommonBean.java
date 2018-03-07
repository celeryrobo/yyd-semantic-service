package com.yyd.service.common;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CommonBean extends AbstractSemanticResult {
	private String semantic;
	
	public CommonBean(String semantic) {
		this.semantic = semantic;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}
}
