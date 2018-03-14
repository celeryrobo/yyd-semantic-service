package com.yyd.service.poetry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.poetry.entity.AuthorEntity;
import com.yyd.service.poetry.entity.PoetryEntity;

@Mapper
public interface AuthorMapper {
	@Select("SELECT p.id,p.title,p.author_name,p.content,pa.caodai FROM tb_poetry p "+
			"INNER JOIN tb_poetry_author pa ON pa.id=p.id "+
			"WHERE p.author_name=#{author}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "dynasty", column = "caodai")
	})
	public List<PoetryEntity> getPoetryByAuthor(String author);
	
	
	
	
	
	
	@Select("SELECT id, name, caodai FROM yyd_resources.tb_author WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "chaodai", column = "caodai")
	})
	public List<AuthorEntity> findByName(String name);
}
