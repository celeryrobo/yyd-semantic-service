package com.yyd.service.common;

import java.lang.reflect.Method;
import java.util.Map;

import com.ybnf.compiler.beans.AbstractSemanticResult;
import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.Semantic;
import com.ybnf.semantic.SemanticContext;

public abstract class AbstractSemantic<T extends AbstractSemanticResult> implements Semantic<T> {
	@SuppressWarnings("unchecked")
	@Override
	public T handle(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		Map<String, String> slots = ybnfCompileResult.getSlots();
		Class<?> clazz = this.getClass();
		try {
			Method method = clazz.getMethod(slots.get("intent"), YbnfCompileResult.class, SemanticContext.class);
			return (T) method.invoke(this, ybnfCompileResult, semanticContext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
