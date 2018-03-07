package com.yyd.service.music;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class MusicBean extends AbstractSemanticResult {
	private Integer id;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
