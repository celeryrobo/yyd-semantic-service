package com.yyd.service.poetry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.poetry.entity.PoetryEntity;
import com.yyd.service.poetry.mapper.AuthorMapper;
import com.yyd.service.poetry.mapper.PoetryMapper;
import com.yyd.service.poetry.mapper.SentenceMapper;

@Service("poetry")
public class PoetrySemantic extends AbstractSemantic<PoetryBean> {
	@Autowired
	private AuthorMapper authorMapper;
	@Autowired
	private PoetryMapper poetryMapper;
	@Autowired
	private SentenceMapper sentenceMapper;
	
	public PoetryBean searchByAuthor(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}

	public PoetryBean searchByTitle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> objects = ybnfCompileResult.getObjects();
		String poetryTitle = objects.get("poetryTitle");
		List<PoetryEntity> poetryEntities = poetryMapper.findByTitle(poetryTitle);
		PoetryBean bean = new PoetryBean();
		if(poetryEntities.isEmpty()) {
			bean.setErrCode(404);
			bean.setText("");
		} else {
			PoetryEntity poetryEntity = poetryEntities.get(0);
			bean.setText(poetryEntity.getContent());
			bean.setResource(poetryEntity);
		}
		return bean;
	}

	public PoetryBean searchByAuthorAndTitle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}

	public PoetryBean nextSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}

	public PoetryBean preSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}

	public PoetryBean firstSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}

	public PoetryBean lastSent(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		PoetryBean bean = new PoetryBean();
		bean.setText("");
		return bean;
	}
}
