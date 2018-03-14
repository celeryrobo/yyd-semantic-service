package com.yyd.service.poetry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.poetry.entity.SentenceEntity;

public interface SentenceMapper {
	@Select("SELECT id,sentence,poetry_id FROM yyd_resources.tb_poetry_sentence WHERE poetry_id in "
			+ "(SELECT poetry_id FROM yyd_resources.tb_poetry_sentence WHERE sentence=#{sentence})")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "sentence", column = "sentence"),
		@Result(property = "poetryId", column = "poetry_id")})
	public List<SentenceEntity> findBySentence(String sentence);
	
	@Select("SELECT id,sentence,poetry_id FROM yyd_resources.tb_poetry_sentence WHERE poetry_id=#{poetryId}")
	@Results({
		@Result(property = "id", column = "id"),
		@Result(property = "sentence", column = "sentence"),
		@Result(property = "poetryId", column = "poetry_id")})
	public List<SentenceEntity> findByPoetryId(Integer poetryId);
}
