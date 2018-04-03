package com.yyd.service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ybnf.compiler.beans.AbstractSemanticResult;

public class CommonBean extends AbstractSemanticResult {
	private String text;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String url;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
