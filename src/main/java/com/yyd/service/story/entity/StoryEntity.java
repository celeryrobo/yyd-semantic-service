package com.yyd.service.story.entity;

public class StoryEntity {
	private Integer id;
	private String story;
	private String url;

	public StoryEntity(Integer id, String story, String url) {
		super();
		this.id = id;
		this.story = story;
		this.url = url;
	}

	@Override
	public String toString() {
		return "StoryEntity [id=" + id + ", story=" + story + ", url=" + url + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
