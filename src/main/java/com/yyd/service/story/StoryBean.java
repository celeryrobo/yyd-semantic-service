package com.yyd.service.story;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class StoryBean extends AbstractSemanticResult {
	private String text;
	private String url;
	
	public StoryBean(int errCode, String errMsg) {
		setErrCode(errCode);
		this.text = errMsg;
	}

	public StoryBean(String url, String text, Object resource) {
		this.url = url;
		this.text = text;
		setOperation(Operation.PLAY);
		setParamType(ParamType.TU);
		setResource(resource);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
