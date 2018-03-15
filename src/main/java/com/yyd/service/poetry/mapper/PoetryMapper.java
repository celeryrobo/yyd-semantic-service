package com.yyd.service.poetry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.poetry.entity.PoetryEntity;

public interface PoetryMapper {

	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM yyd_resources.tb_poetry p "
			+ "INNER JOIN yyd_resources.tb_poetry_author pa ON pa.id=p.id " + "WHERE p.title=#{title}")
	@Results({ @Result(property = "authorName", column = "author_name"),
			@Result(property = "dynasty", column = "caodai") })
	public List<PoetryEntity> findByTitle(String title);

	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM yyd_resources.tb_poetry p "
			+ "INNER JOIN yyd_resources.tb_poetry_author pa ON pa.id=p.id " + "WHERE p.author_name=#{author}")
	@Results({ @Result(property = "authorName", column = "author_name"),
			@Result(property = "dynasty", column = "caodai") })
	public List<PoetryEntity> findByAuthor(String author);

	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM yyd_resources.tb_poetry p "
			+ "INNER JOIN yyd_resources.tb_poetry_author pa ON pa.id=p.id "
			+ "WHERE p.author_name=#{author} AND p.title=#{title}")
	@Results({ @Result(property = "authorName", column = "author_name"),
			@Result(property = "dynasty", column = "caodai") })
	public List<PoetryEntity> findByAuthorAndTitle(@Param("author") String author, @Param("title") String title);

	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM yyd_resources.tb_poetry p "
			+ "INNER JOIN yyd_resources.tb_poetry_author pa ON pa.id=p.id " + "WHERE p.id=#{id}")
	@Results({ @Result(property = "authorName", column = "author_name"),
			@Result(property = "dynasty", column = "caodai") })
	public PoetryEntity getById(Integer id);

	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM yyd_resources.tb_poetry p "
			+ "INNER JOIN yyd_resources.tb_poetry_author pa ON pa.id=p.id " + "LIMIT 1000")
	@Results({ @Result(property = "authorName", column = "author_name"),
			@Result(property = "dynasty", column = "caodai") })
	public List<PoetryEntity> findRandom();
}
