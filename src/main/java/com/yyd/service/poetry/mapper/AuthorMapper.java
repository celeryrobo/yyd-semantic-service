package com.yyd.service.poetry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.poetry.entity.AuthorEntity;

@Mapper
public interface AuthorMapper {
	@Select("SELECT id, name, caodai FROM yyd_resources.tb_author WHERE id = #{id}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "chaodai", column = "caodai")
	})
	public AuthorEntity getById(Integer id);
	
	@Select("SELECT id, name, caodai FROM yyd_resources.tb_author WHERE name = #{name}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "chaodai", column = "caodai")
	})
	public List<AuthorEntity> findByName(String name);
}
