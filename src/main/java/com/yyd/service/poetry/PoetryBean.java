package com.yyd.service.poetry;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class PoetryBean extends AbstractSemanticResult {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
