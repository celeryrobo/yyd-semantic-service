package com.yyd.service.story.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.story.entity.AlbumEntity;
import com.yyd.service.story.entity.StoryEntity;


@Mapper
public interface StoryMapper {
	@Select("SELECT album_id,album_name FROM story.tb_album_category WHERE category_name = #{categoryName}")
	@Results({ 	@Result(property = "name", column = "album_name")})
	public List<AlbumEntity> getByCategory(String categoryName);
	
	@Select("SELECT story_name,story_url FROM story.tb_story WHERE album_name = #{albumName}")
	@Results({ 	@Result(property = "name", column = "story_name"),
				@Result(property = "url", column = "story_url")})
	public List<StoryEntity> getByAlbum(String albumName);
	
	@Select("SELECT story_name,story_url FROM story.tb_story WHERE story_name = #{storyName}")
	@Results({ 	@Result(property = "name", column = "story_name"),
				@Result(property = "url", column = "story_url")})
	public List<StoryEntity> getByStory(String storyName);
}
