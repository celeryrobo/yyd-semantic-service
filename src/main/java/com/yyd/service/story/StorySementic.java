package com.yyd.service.story;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.story.entity.StoryEntity;
import com.yyd.service.story.mapper.StoryMapper;
import com.yyd.service.utils.CommonUtils;

@Service("story")
public class StorySementic extends AbstractSemantic<StoryBean> {
	@Autowired
	private StoryMapper storyMapper;
	public static final Integer SEMANTIC_FAILURE_CODE = 2001;
	public static final String SEMANTIC_FAILURE_TEXT = "抱歉，没有找到相关资源";
	public static final String ANSWER_TEXT = "好的，我要开始讲了";

	public StoryBean searchByCategory(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String category = object.get("storyCategory");
		return getResult(storyMapper.findByCategory(category));
	}

	public StoryBean searchByName(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String name = object.get("storyName");
		return getResult(storyMapper.findByName(name));
	}

	public StoryBean searchByAlbum(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String album = object.get("storyAlbum");
		return getResult(storyMapper.findByAlbum(album));
	}

	public StoryBean searchRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult(storyMapper.findRandom());
	}

	private StoryBean getResult(List<StoryEntity> storyEntities) {
		if (storyEntities.isEmpty()) {
			return new StoryBean(SEMANTIC_FAILURE_CODE, SEMANTIC_FAILURE_TEXT);
		} else {
			int randomNum = CommonUtils.randomInt(storyEntities.size());
			StoryEntity story = storyEntities.get(randomNum);
			return new StoryBean(story.getUrl(), ANSWER_TEXT, story);
		}
	}
}
