package com.yyd.service.poetry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.poetry.entity.PoetryEntity;

public interface PoetryMapper {
	@Select("SELECT id, title, author_name, author_id, content FROM yyd_resources.tb_poetry WHERE title = #{title}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "title", column = "title"),
		@Result(property = "authorName", column = "author_name"),
		@Result(property = "authorId", column = "author_id"),
		@Result(property = "content", column = "content")
	})
	public List<PoetryEntity> findByTitle(String title);
}
