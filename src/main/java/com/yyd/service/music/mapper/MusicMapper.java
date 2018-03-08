package com.yyd.service.music.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.music.entity.MusicEntity;

@Mapper
public interface MusicMapper {
	@Select("SELECT song_name,songUrl,artist_name FROM music.song WHERE song_name = #{name}")
	@Results({ @Result(property = "name", column = "song_name"), @Result(property = "url", column = "songUrl"),
			@Result(property = "singer", column = "artist_name"), })
	public List<MusicEntity> getByName(String name);

	@Select("SELECT song_name,songUrl,artist_name FROM music.song WHERE artist_name = #{singer}")
	@Results({ @Result(property = "name", column = "song_name"), @Result(property = "url", column = "songUrl"),
			@Result(property = "singer", column = "artist_name"), })
	public List<MusicEntity> getByAuthor(String singer);

	@Select("SELECT song_name,songUrl,artist_name FROM music.song WHERE artist_name = #{singer} and song_name = #{song}")
	@Results({ @Result(property = "name", column = "song_name"), @Result(property = "url", column = "songUrl"),
			@Result(property = "singer", column = "artist_name"), })
	public List<MusicEntity> getByAuthorAndSong(@Param("singer") String singer, @Param("song") String song);

	@Select("SELECT song_name,songUrl,artist_name FROM music.song LIMIT 100")
	@Results({ @Result(property = "name", column = "song_name"), @Result(property = "url", column = "songUrl"),
			@Result(property = "singer", column = "artist_name"), })
	public List<MusicEntity> getRandom();
}
