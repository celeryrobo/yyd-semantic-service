package com.yyd.service.poetry;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PoetryBean extends AbstractSemanticResult {
	private String text;
	
	public PoetryBean() {
	}

	public PoetryBean(int errCode) {
		setErrCode(errCode);
	}

	public PoetryBean(String text, Object resource) {
		this.text = text;
		setResource(resource);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
