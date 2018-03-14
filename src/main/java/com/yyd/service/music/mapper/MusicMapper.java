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
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (kuwo_music.tb_song s,kuwo_music.tb_artist a)"+
			 "INNER JOIN kuwo_music.tb_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE a.artist_name = #{singer}")
	@Results({  @Result(property = "id", column = "tas.id"), 
				@Result(property = "singer", column = "a.artist_name"),
				@Result(property = "song", column = "s.song_name"), 
				@Result(property = "url", column = "s.src_url")})
	public List<MusicEntity> getMusicBySinger(String singer);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (kuwo_music.tb_song s,kuwo_music.tb_artist a)"+
			 "INNER JOIN kuwo_music.tb_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE s.song_name = #{songName}")
	@Results({  @Result(property = "id", column = "tas.id"), 
				@Result(property = "singer", column = "a.artist_name"),
				@Result(property = "song", column = "s.song_name"), 
				@Result(property = "url", column = "s.src_url")})
	public List<MusicEntity> getMusicByName(String songName);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (kuwo_music.tb_song s,kuwo_music.tb_artist a)"+
			 "INNER JOIN kuwo_music.tb_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE s.song_name = #{songName} and a.artist_name = #{singer}")
	@Results({  @Result(property = "id", column = "tas.id"), 
				@Result(property = "singer", column = "a.artist_name"),
				@Result(property = "song", column = "s.song_name"), 
				@Result(property = "url", column = "s.src_url")})
	public List<MusicEntity> getMusicByNameAndArtist(@Param("songName") String songName, @Param("singer") String singer);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (kuwo_music.tb_song s,kuwo_music.tb_artist a)"+
			 "INNER JOIN kuwo_music.tb_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "Limit 500")
	@Results({  @Result(property = "id", column = "tas.id"), 
				@Result(property = "singer", column = "a.artist_name"),
				@Result(property = "song", column = "s.song_name"), 
				@Result(property = "url", column = "s.src_url")})
	public List<MusicEntity> getMusicByRandom();
}
