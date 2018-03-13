package com.yyd.service.story;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.story.entity.AlbumEntity;
import com.yyd.service.story.entity.StoryEntity;
import com.yyd.service.story.mapper.StoryMapper;
import com.yyd.service.utils.CommonUtils;

@Service("story")
public class StorySementic extends AbstractSemantic<StoryBean>{
	@Autowired
	private StoryMapper storyMapper;
	public static final Integer SEMANTIC_FAILURE = 201;
	
	public StoryBean searchByName(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String name = object.get("storyName");
		List<StoryEntity> storyEntities = storyMapper.getByStory(name);
		if (storyEntities.isEmpty()) {
			return new StoryBean(SEMANTIC_FAILURE);
		} else {
			StoryEntity storyEntity = storyEntities.get(0);
			return new StoryBean(storyEntity.getUrl(), storyEntity);
		}
	}
	
	public StoryBean searchByAlbum(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String album = object.get("storyAlbum");
		List<StoryEntity> storyEntities = storyMapper.getByAlbum(album);
		if (storyEntities.isEmpty()) {
			return new StoryBean(SEMANTIC_FAILURE);
		} else {
			int randomIdx = CommonUtils.randomInt(storyEntities.size());
			StoryEntity storyEntity = storyEntities.get(randomIdx);
			return new StoryBean(storyEntity.getUrl(), storyEntity);
		}
	}
	
	public StoryBean searchByCategory(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String category = object.get("storyCategory");
		List<AlbumEntity> albumEntities = storyMapper.getByCategory(category);
		if (albumEntities.isEmpty()) {
			return new StoryBean(SEMANTIC_FAILURE);
		} else {
			int randomAlbumIdx = CommonUtils.randomInt(albumEntities.size());
			List<StoryEntity> storyEntities=storyMapper.getByAlbum(albumEntities.get(randomAlbumIdx).getName());
			if(storyEntities.isEmpty()) {
				return new StoryBean(SEMANTIC_FAILURE);
			}else {
				int randomStoryIdx = CommonUtils.randomInt(storyEntities.size());
				StoryEntity storyEntity = storyEntities.get(randomStoryIdx);
				return new StoryBean(storyEntity.getUrl(), storyEntity);
			}
		}
	}
	
	public StoryBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<AlbumEntity> albumEntities = storyMapper.getByCategory("故事");
		if (albumEntities.isEmpty()) {
			return new StoryBean(SEMANTIC_FAILURE);
		} else {
			int randomAlbumIdx = CommonUtils.randomInt(albumEntities.size());
			List<StoryEntity> storyEntities=storyMapper.getByAlbum(albumEntities.get(randomAlbumIdx).getName());
			if(storyEntities.isEmpty()) {
				return new StoryBean(SEMANTIC_FAILURE);
			}else {
				int randomStoryIdx = CommonUtils.randomInt(storyEntities.size());
				StoryEntity storyEntity = storyEntities.get(randomStoryIdx);
				return new StoryBean(storyEntity.getUrl(), storyEntity);
			}
		}
	}
}
