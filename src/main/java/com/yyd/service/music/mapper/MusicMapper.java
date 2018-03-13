package com.yyd.service.music.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.yyd.service.music.entity.ArtistEntity;
import com.yyd.service.music.entity.MusicEntity;
import com.yyd.service.music.entity.SongEntity;

@Mapper
public interface MusicMapper {
	@Select("SELECT id,song_name,src_url FROM kuwo_music.tb_song WHERE song_name = #{name}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "name", column = "song_name"), 
				@Result(property = "url", column = "src_url")})
	public List<SongEntity> getSongByName(String name);
	
	@Select("SELECT id,song_name,src_url FROM kuwo_music.tb_song WHERE id = #{id}")
	@Results({  @Result(property = "id", column = "id"), 
				@Result(property = "name", column = "song_name"), 
				@Result(property = "url", column = "src_url") })
	public SongEntity getSongById(Integer id);

	@Select("SELECT id,artist_name FROM kuwo_music.tb_artist WHERE artist_name = #{singer}")
	@Results({  @Result(property = "id", column = "song_name"), 
				@Result(property = "singer", column = "artist_name")})
	public ArtistEntity getAuthorByName (String singer);

	@Select("SELECT id FROM kuwo_music.tb_artist_song WHERE song_id = #{songId} and artist_id = #{artistId}")
	@Results({  @Result(property = "id", column = "id")})
	public List<MusicEntity> getAuthorAndSongById(@Param("songId") Integer songId, @Param("artistId") Integer artistId);
	
	@Select("SELECT song_id FROM kuwo_music.tb_artist_song WHERE artist_id = #{artistId}")
	public List<Integer> getSongIdByArtistId(Integer artistId);
	
	@Select("SELECT artist_id FROM kuwo_music.tb_artist_song WHERE song_id = #{songId}")
	public List<Integer> getArtistIdBySongId(Integer songId);
	
	@Select("SELECT id,song_name,src_url FROM kuwo_music.tb_song LIMIT 400")
	@Results({ 	@Result(property = "id", column = "id"), 
				@Result(property = "name", column = "song_name"), 
				@Result(property = "url", column = "src_url") })
	public List<SongEntity> getRandom();
	
	@Select("SELECT id,artist_name FROM kuwo_music.tb_artist WHERE artist_name = #{singer}")
	@Results({ 	@Result(property = "id", column = "id"), 
				@Result(property = "name", column = "song_name"), 
				@Result(property = "url", column = "src_url") })
	public List<SongEntity> getAuthorById(int id);
}
