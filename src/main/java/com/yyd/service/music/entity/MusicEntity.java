package com.yyd.service.music.entity;

public class MusicEntity {
	private Integer id;
	private String singer;
	private String song;
	private String url;

	public MusicEntity(Integer id, String singer, String song, String url) {
		super();
		this.id = id;
		this.singer = singer;
		this.song = song;
		this.url = url;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
