package com.yyd.service.common;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CommonBean extends AbstractSemanticResult {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
