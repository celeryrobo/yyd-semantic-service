package com.yyd.service.music;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.music.entity.ArtistEntity;
import com.yyd.service.music.entity.SongEntity;
import com.yyd.service.music.mapper.MusicMapper;
import com.yyd.service.utils.CommonUtils;

@Service("music")
public class MusicSemantic extends AbstractSemantic<MusicBean> {
	@Autowired
	private MusicMapper musicMapper;
	public static final Integer SEMANTIC_FAILURE = 201;

	public MusicBean searchBySinger(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String singer = object.get("singer");
		ArtistEntity artist = musicMapper.getAuthorByName(singer);
		if (artist==null) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			List<Integer> songId=musicMapper.getSongIdByArtistId(artist.getId());
			if(songId.isEmpty()) {
				return new MusicBean(SEMANTIC_FAILURE);
			}else {
				int id=songId.get(CommonUtils.randomInt(songId.size()));
				SongEntity song=musicMapper.getSongById(id);
				return new MusicBean(song.getUrl(), song);
			}
		}
	}

	public MusicBean searchBySong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		List<SongEntity> songEntities = musicMapper.getSongByName(song);
		if (songEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			SongEntity songEntity = songEntities.get(0);
			return new MusicBean(songEntity.getUrl(), songEntity);
		}
	}

	public MusicBean searchBySingerAndSong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		String singer = object.get("singer");
		ArtistEntity artist=musicMapper.getAuthorByName(singer);
		List<SongEntity> songEntities=musicMapper.getSongByName(song);
		if(songEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		}else {
			for (SongEntity songEntity : songEntities) {
				if(musicMapper.getAuthorAndSongById(songEntity.getId(), artist.getId())!=null) {
					return new MusicBean(songEntity.getUrl(), songEntity);
				}
			}
		}
		return new MusicBean(SEMANTIC_FAILURE);
	}

	public MusicBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<SongEntity> songEntities = musicMapper.getRandom();
		if (songEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			int randomIdx = CommonUtils.randomInt(songEntities.size());
			SongEntity songEntity = songEntities.get(randomIdx);
			return new MusicBean(songEntity.getUrl(), songEntity);
		}
	}
}
