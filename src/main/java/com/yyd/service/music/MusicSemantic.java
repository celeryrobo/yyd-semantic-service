package com.yyd.service.music;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.compiler.beans.AbstractSemanticResult.Operation;
import com.ybnf.compiler.beans.AbstractSemanticResult.ParamType;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.music.entity.MusicEntity;
import com.yyd.service.music.entity.TagEntity;
import com.yyd.service.music.mapper.MusicMapper;
import com.yyd.service.utils.CommonUtils;

@Service("music")
public class MusicSemantic extends AbstractSemantic<MusicBean> {
	@Autowired
	private MusicMapper musicMapper;
	public static final Integer SEMANTIC_FAILURE_CODE = 2001;
	public static final String SEMANTIC_FAILURE_TEXT = "抱歉，没有找到相关资源";
	public static final String MUSIC_ANSWER_TEXT = "好的，我要开始唱了";
	
	public MusicBean searchBySinger(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String singer = object.get("singer");
		List<MusicEntity> musicEntities = musicMapper.findBySinger(singer);
		return getResult(musicEntities);
	}

	public MusicBean searchBySong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		List<MusicEntity> musicEntities = musicMapper.findBySong(song);
		return getResult(musicEntities);
	}
	
	public MusicBean searchByTag(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		MusicBean bean=new MusicBean();
		Map<String, String> object = ybnfCompileResult.getObjects();
		String tag = object.get("songTag");
		List<TagEntity> te = musicMapper.findByTag(tag);
		if(te.isEmpty()) {
			bean.setErrCode(SEMANTIC_FAILURE_CODE);
			bean.setText(SEMANTIC_FAILURE_TEXT);
			return bean;
		}else {
			int randomNum = CommonUtils.randomInt(te.size());
			TagEntity tagEntity=te.get(randomNum);
			tagEntity.setUrl("www.tag.song.mp3");
			bean.setText(MUSIC_ANSWER_TEXT);
			bean.setUrl(tagEntity.getUrl());
			bean.setOperation(Operation.PLAY);
			bean.setParamType(ParamType.TU);
			bean.setResource(tagEntity);
			return bean;
		}
	}

	public MusicBean searchBySingerAndSong(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String song = object.get("song");
		String singer = object.get("singer");
		List<MusicEntity> musicEntities = musicMapper.findBySongAndArtist(song, singer);
		if(musicEntities.isEmpty()) {
			musicEntities=musicMapper.findBySong(song);
			if(musicEntities.isEmpty()) {
				musicEntities=musicMapper.findBySinger(singer);
			}
		}
		return getResult(musicEntities);
	}

	public MusicBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<MusicEntity> musicEntities = musicMapper.findRandom();
		return getResult(musicEntities);
	}

	private MusicBean getResult(List<MusicEntity> musicEntities) {
		MusicBean bean=new MusicBean();
		if (musicEntities.isEmpty()) {
			bean.setErrCode(SEMANTIC_FAILURE_CODE);
			bean.setText(SEMANTIC_FAILURE_TEXT);
			return bean;
		} else {
			int randomNum = CommonUtils.randomInt(musicEntities.size());
			MusicEntity music = musicEntities.get(randomNum);
			bean.setText(MUSIC_ANSWER_TEXT);
			bean.setUrl(music.getUrl());
			bean.setOperation(Operation.PLAY);
			bean.setParamType(ParamType.TU);
			bean.setResource(music);
			return bean;
		}
	}
}
