package com.yyd.service.poetry.entity;

public class SentenceEntity {
	private Integer id;
	private String sentence;
	private Integer poetryId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Integer getPoetryId() {
		return poetryId;
	}

	public void setPoetryId(Integer poetryId) {
		this.poetryId = poetryId;
	}
}
