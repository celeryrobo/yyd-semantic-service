package com.yyd.service.domain;

public class SemanticPostEntity {
	private String lang;
	private String userIdentify;
	private String semantic = null;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getUserIdentify() {
		return userIdentify;
	}

	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Override
	public String toString() {
		return "SemanticPostEntity [lang=" + lang + ", userIdentify=" + userIdentify + ", semantic=" + semantic + "]";
	}
}
