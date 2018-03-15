package com.yyd.service.poetry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.poetry.entity.PoetryEntity;
import com.yyd.service.poetry.entity.SentenceEntity;
import com.yyd.service.poetry.mapper.PoetryMapper;
import com.yyd.service.poetry.mapper.SentenceMapper;
import com.yyd.service.utils.CommonUtils;

@Service("poetry")
public class PoetrySemantic extends AbstractSemantic<PoetryBean> {
	@Autowired
	private PoetryMapper poetryMapper;
	@Autowired
	private SentenceMapper sentenceMapper;
	public static final Integer SEMANTIC_FAILURE = 201;

	public PoetryBean searchByAuthor(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> object = ybnfCompileResult.getObjects();
		String author = object.get("poetryAuthor");
		List<PoetryEntity> poetryEntities = poetryMapper.findByAuthor(author);
		return getResult(poetryEntities);
	}

	public PoetryBean searchByTitle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String title = objects.get("poetryTitle");
		List<PoetryEntity> poetryEntities = poetryMapper.findByTitle(title);
		return getResult(poetryEntities);
	}

	public PoetryBean searchByAuthorAndTitle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String author = objects.get("poetryAuthor");
		String title = objects.get("poetryTitle");
		List<PoetryEntity> poetryEntities=poetryMapper.findByAuthorAndTitle(author, title);
		if(poetryEntities.isEmpty()) {
			poetryEntities=poetryMapper.findByTitle(title);
			if(poetryEntities.isEmpty()) {
				poetryEntities=poetryMapper.findByAuthor(author);
			}
		}
		return getResult(poetryEntities);
	}
	
	public PoetryBean searchByRandom(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		List<PoetryEntity> poetryEntities=poetryMapper.findRandom();
		return getResult(poetryEntities);
	}
	
	public PoetryBean nextSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String sentence = objects.get("poetrySentence");
		List<SentenceEntity> sentenceEntites = sentenceMapper.findBySentence(sentence);
		for (SentenceEntity sentenceEntity : sentenceEntites) {
			if (sentenceEntity.getSentence().equals(sentence)) {
				int id = sentenceEntity.getId();
				id = id + 1;
				for (SentenceEntity senEntity : sentenceEntites) {
					if (senEntity.getId().equals(id)) {
						bean.setText(senEntity.getSentence());
						bean.setResource(poetryMapper.getById(senEntity.getPoetryId()));
						return bean;
					}
				}
				bean.setText("已经是最后一句了。");
				bean.setResource(poetryMapper.getById(sentenceEntity.getPoetryId()));
				return bean;
			}
		}
		return bean;
	}

	public PoetryBean preSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String sentence = objects.get("poetrySentence");
		List<SentenceEntity> sentenceEntites = sentenceMapper.findBySentence(sentence);
		for (SentenceEntity sentenceEntity : sentenceEntites) {
			if (sentenceEntity.getSentence().equals(sentence)) {
				int id = sentenceEntity.getId();
				id = id - 1;
				for (SentenceEntity senEntity : sentenceEntites) {
					if (senEntity.getId().equals(id)) {
						bean.setText(senEntity.getSentence());
						System.out.println(poetryMapper.getById(senEntity.getPoetryId()));
						bean.setResource(poetryMapper.getById(senEntity.getPoetryId()));
						return bean;
					}
				}
				bean.setText("已经是第一句了。");
				bean.setResource(poetryMapper.getById(sentenceEntity.getPoetryId()));
				return bean;
			}
		}
		return bean;
	}
	
	

	private PoetryBean getResult(List<PoetryEntity> poetryEntities) {
		if (poetryEntities.isEmpty()) {
			return new PoetryBean(SEMANTIC_FAILURE);
		} else {
			int randomNum = CommonUtils.randomInt(poetryEntities.size());
			PoetryEntity poetry = poetryEntities.get(randomNum);
			StringBuffer text = new StringBuffer();
			text.append(poetry.getTitle()).append("，").append(poetry.getAuthorName()).append("，")
					.append(poetry.getContent());
			return new PoetryBean(text.toString(), poetry);
		}
	}

	public PoetryBean firstSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String title = objects.get("poetryTitle");
		List<PoetryEntity> poetryEntities = poetryMapper.findByTitle(title);
		if (poetryEntities.isEmpty()) {
			bean.setErrCode(SEMANTIC_FAILURE);
			return bean;
		} else {
			int randomNum = CommonUtils.randomInt(poetryEntities.size());
			PoetryEntity poetry = poetryEntities.get(randomNum);
			List<SentenceEntity> sentenceEntities = sentenceMapper.findByPoetryId(poetry.getId());
			if (sentenceEntities.isEmpty()) {
				bean.setErrCode(SEMANTIC_FAILURE);
				return bean;
			} else {
				SentenceEntity sentence = sentenceEntities.get(0);
				bean.setText(sentence.getSentence());
				bean.setResource(poetry);
				return bean;
			}
		}
	}

	public PoetryBean lastSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String title = objects.get("poetryTitle");
		List<PoetryEntity> poetryEntities = poetryMapper.findByTitle(title);
		if (poetryEntities.isEmpty()) {
			bean.setErrCode(SEMANTIC_FAILURE);
			return bean;
		} else {
			int randomNum = CommonUtils.randomInt(poetryEntities.size());
			PoetryEntity poetry = poetryEntities.get(randomNum);
			List<SentenceEntity> sentenceEntities = sentenceMapper.findByPoetryId(poetry.getId());
			if (sentenceEntities.isEmpty()) {
				bean.setErrCode(SEMANTIC_FAILURE);
				return bean;
			} else {
				int index = sentenceEntities.size() - 1;
				SentenceEntity sentence = sentenceEntities.get(index);
				bean.setText(sentence.getSentence());
				bean.setResource(poetry);
				return bean;
			}
		}
	}
}
