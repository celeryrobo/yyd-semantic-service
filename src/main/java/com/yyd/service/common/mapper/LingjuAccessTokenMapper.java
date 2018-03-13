package com.yyd.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.domain.LingjuAccessToken;

@Mapper
public interface LingjuAccessTokenMapper {
	@Select("SELECT COUNT(*) FROM robot_user.lingju_accesstoken")
	public Integer count();

	@Select("SELECT i, userId, accessToken FROM robot_user.lingju_accesstoken")
	@Results({
		@Result(property = "id", column = "i"),
		@Result(property = "userId", column = "userId"),
		@Result(property = "accessToken", column = "accessToken")
	})
	public List<LingjuAccessToken> findAll();
}
