package com.yyd.service.music;

import com.ybnf.compiler.beans.AbstractSemanticResult;


public class MusicBean extends AbstractSemanticResult {
	private String text;
	private String url;
	public MusicBean() {
	}
	public MusicBean(int errCode) {
		setErrCode(errCode);
	}
	public MusicBean(String url,Object resource) {
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
	
	public String getText() {
		return text;
	}
	public void setText(String answer) {
		this.text = answer;
	}
}
