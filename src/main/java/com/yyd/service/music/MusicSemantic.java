package com.yyd.service.music;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.common.CommonUtils;
import com.yyd.service.music.entity.MusicEntity;
import com.yyd.service.music.mapper.MusicMapper;

@Service("music")
public class MusicSemantic extends AbstractSemantic<MusicBean> {
	@Autowired
	private MusicMapper musicMapper;
	public static final Integer SEMANTIC_FAILURE = 201;

	public MusicBean searchBySinger(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String singer = object.get("singer");
		List<MusicEntity> musicEntities = musicMapper.getByAuthor(singer);
		if (musicEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			MusicEntity musicEntity = musicEntities.get(0);
			return new MusicBean(musicEntity.getUrl(), musicEntity);
		}
	}

	public MusicBean searchBySong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		List<MusicEntity> musicEntities = musicMapper.getByName(song);
		if (musicEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			MusicEntity musicEntity = musicEntities.get(0);
			return new MusicBean(musicEntity.getUrl(), musicEntity);
		}
	}

	public MusicBean searchBySingerAndSong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		String singer = object.get("singer");
		List<MusicEntity> musicEntities = musicMapper.getByAuthorAndSong(singer, song);
		if (musicEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			MusicEntity musicEntity = musicEntities.get(0);
			return new MusicBean(musicEntity.getUrl(), musicEntity);
		}
	}

	public MusicBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<MusicEntity> musicEntities = musicMapper.getRandom();
		if (musicEntities.isEmpty()) {
			return new MusicBean(SEMANTIC_FAILURE);
		} else {
			int randomIdx = CommonUtils.randomInt(musicEntities.size());
			MusicEntity musicEntity = musicEntities.get(randomIdx);
			return new MusicBean(musicEntity.getUrl(), musicEntity);
		}

	}
}
