package com.yyd.service.story.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.story.entity.StoryEntity;


@Mapper
public interface StoryMapper {
	@Select( "SELECT s.id,s.story_name,s.story_url FROM story.tb_story s " +
			 "INNER JOIN story.tb_album_category ac ON ac.album_id = s.album_id " +
			 "WHERE ac.category_name = #{categoryName}")
	@Results({ 	@Result(property = "id", column = "s.id"),
				@Result(property = "story", column = "s.story_name"),
				@Result(property = "url", column = "s.story_url")})
	public List<StoryEntity> getStoryByCategory(String categoryName);
	
	@Select("SELECT id,story_name,story_url FROM story.tb_story WHERE story_name = #{storyName}")
	@Results({ 	@Result(property = "id", column = "id"),
				@Result(property = "story", column = "story_name"),
				@Result(property = "url", column = "story_url")})
	public List<StoryEntity> getStoryByName(String storyName);	
	
	@Select( "SELECT s.id,s.story_name,s.story_url FROM story.tb_story s " +
			 "INNER JOIN story.tb_album_category ac ON ac.album_id = s.album_id " +
			 "WHERE ac.album_name = #{albumName}")
	@Results({ 	@Result(property = "id", column = "s.id"),
				@Result(property = "story", column = "s.story_name"),
				@Result(property = "url", column = "s.story_url")})
	public List<StoryEntity> getStoryByAlbum(String albumName);

	@Select( "SELECT s.id,s.story_name,s.story_url FROM story.tb_story s " +
			 "INNER JOIN story.tb_album_category ac ON ac.album_id = s.album_id " +
			 "LIMIT 1000")
	@Results({ 	@Result(property = "id", column = "s.id"),
				@Result(property = "story", column = "s.story_name"),
				@Result(property = "url", column = "s.story_url")})
	public List<StoryEntity> getStoryByRandom();

}
