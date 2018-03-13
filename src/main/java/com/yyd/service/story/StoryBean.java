package com.yyd.service.story;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class StoryBean extends AbstractSemanticResult{
	private String url;
	public StoryBean(int errCode) {
		setErrCode(errCode);
	}
	public StoryBean(String url,Object resource) {
		this.url = url;
		setOperation(Operation.PLAY);
		setParamType(ParamType.U);
		setResource(resource);
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
