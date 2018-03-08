package com.yyd.service.music;

import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;

@Service("music")
public class MusicSemantic extends AbstractSemantic<MusicBean> {
	public MusicBean play(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		String text = ybnfCompileResult.toString();
		semanticContext.getParams().putAll(ybnfCompileResult.getSlots());
		semanticContext.getParams().putAll(ybnfCompileResult.getObjects());
		MusicBean rs = new MusicBean();
		rs.setId(1);
		rs.setName(text);
		rs.setErrCode(0);
		return rs;
	}
}
