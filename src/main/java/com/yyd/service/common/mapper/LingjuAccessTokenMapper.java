package com.yyd.service.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yyd.service.domain.LingjuAccessToken;

@Mapper
public interface LingjuAccessTokenMapper {
	@Select("SELECT COUNT(*) FROM yyd_resources.lingju_accesstoken")
	public Integer count();

	@Select("SELECT i, userId, accessToken FROM yyd_resources.lingju_accesstoken")
	@Results({ @Result(property = "id", column = "i"), @Result(property = "userId", column = "userId"),
			@Result(property = "accessToken", column = "accessToken") })
	public List<LingjuAccessToken> findAll();

	@Select("SELECT i, userId, accessToken FROM yyd_resources.lingju_accesstoken WHERE userId=#{userId} LIMIT 1")
	@Results({ @Result(property = "id", column = "i"), @Result(property = "userId", column = "userId"),
			@Result(property = "accessToken", column = "accessToken") })
	public LingjuAccessToken getByUserId(String userId);

	@Insert("INSERT INTO yyd_resources.lingju_accesstoken(userId, accessToken) VALUES(#{userId}, #{accessToken})")
	public void add(LingjuAccessToken lingjuAccessToken);

	@Update("UPDATE yyd_resources.lingju_accesstoken SET accessToken = ${accessToken} WHERE userId = #{userId}")
	public void update(LingjuAccessToken lingjuAccessToken);
}
