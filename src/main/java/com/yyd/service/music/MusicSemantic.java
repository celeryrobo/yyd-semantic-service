package com.yyd.service.music;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.music.entity.MusicEntity;
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
		List<MusicEntity> musicEntities = musicMapper.getMusicBySinger(singer);
		return getResult(musicEntities);
	}

	public MusicBean searchBySong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		List<MusicEntity> musicEntities = musicMapper.getMusicByName(song);
		return getResult(musicEntities);
	}

	public MusicBean searchBySingerAndSong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		String singer = object.get("singer");
		List<MusicEntity> musicEntities = musicMapper.getMusicByNameAndArtist(song, singer);
		return getResult(musicEntities);
	}

	public MusicBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<MusicEntity> musicEntities = musicMapper.getMusicByRandom();
		return getResult(musicEntities);
	}

	private MusicBean getResult(List<MusicEntity> musicEntities) {
		if (musicEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			int randomNum = CommonUtils.randomInt(musicEntities.size());
			MusicEntity music = musicEntities.get(randomNum);
			return new MusicBean(music.getUrl(), music);
		}
	}
}
