package com.yyd.service.music.entity;

public class MusicEntity {
	private int id;
	private String name;
	private String url;
	private String singer;

	@Override
	public String toString() {
		return "MusicEntity [id=" + id + ", name=" + name + ", url=" + url + ", singer=" + singer + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
