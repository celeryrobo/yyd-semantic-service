package com.yyd.service.music.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.music.entity.MusicEntity;
import com.yyd.service.music.entity.TagEntity;

@Mapper
public interface MusicMapper {
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (yyd_resources.tb_music_song s,yyd_resources.tb_music_artist a)"+
			 "INNER JOIN yyd_resources.tb_music_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE a.artist_name = #{singer}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "singer", column = "artist_name"),
				@Result(property = "song", column = "song_name"), 
				@Result(property = "url", column = "src_url")})
	public List<MusicEntity> findBySinger(String singer);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (yyd_resources.tb_music_song s,yyd_resources.tb_music_artist a)"+
			 "INNER JOIN yyd_resources.tb_music_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE s.song_name = #{songName}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "singer", column = "artist_name"),
				@Result(property = "song", column = "song_name"), 
				@Result(property = "url", column = "src_url")})
	public List<MusicEntity> findBySong(String songName);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (yyd_resources.tb_music_song s,yyd_resources.tb_music_artist a)"+
			 "INNER JOIN yyd_resources.tb_music_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "WHERE s.song_name = #{songName} and a.artist_name = #{singer}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "singer", column = "artist_name"),
				@Result(property = "song", column = "song_name"), 
				@Result(property = "url", column = "src_url")})
	public List<MusicEntity> findBySongAndArtist(@Param("songName") String songName, @Param("singer") String singer);
	
	@Select( "SELECT tas.id,s.song_name,a.artist_name,s.src_url FROM (yyd_resources.tb_music_song s,yyd_resources.tb_music_artist a)"+
			 "INNER JOIN yyd_resources.tb_music_artist_song tas ON tas.artist_id = a.id and tas.song_id=s.id " + 
			 "Limit 500")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "singer", column = "artist_name"),
				@Result(property = "song", column = "song_name"), 
				@Result(property = "url", column = "src_url")})
	public List<MusicEntity> findRandom();
	
	@Select( "SELECT * FROM yyd_resources.tb_music_temp_tag WHERE tag_name = #{tag}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "tag", column = "tag_name"),
				@Result(property = "song", column = "song")})
	public List<TagEntity> findByTag(String tag);
}
