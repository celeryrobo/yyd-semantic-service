package com.yyd.service.robotcontrol;

import org.springframework.stereotype.Service;

import com.ybnf.compiler.beans.YbnfCompileResult;
import com.ybnf.semantic.SemanticContext;
import com.yyd.service.common.AbstractSemantic;
import com.yyd.service.music.MusicBean;

@Service("robotControl")
public class RobotControlSemantic extends AbstractSemantic<MusicBean> {
	private RobotControlBean getResult(String command) {
		RobotControlBean bean = new RobotControlBean();
		bean.setText("好的，主人");
		bean.setCommand(command);
		return bean;
	}

	public RobotControlBean forward(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("forward");
	}

	public RobotControlBean stop(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("stop");
	}

	public RobotControlBean back(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("back");
	}

	public RobotControlBean left(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("left");
	}

	public RobotControlBean right(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("right");
	}

	public RobotControlBean headUp(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("headUp");
	}

	public RobotControlBean headDown(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("headDown");
	}

	public RobotControlBean shutdown(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("shutdown");
	}

	public RobotControlBean switchFormalVersion(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("switchFormalVersion");
	}

	public RobotControlBean switchDebugVersion(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("switchDebugVersion");
	}

	public RobotControlBean video(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("video");
	}

	public RobotControlBean volumeUp(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("volumeUp");
	}

	public RobotControlBean volumeMax(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("volumeMax");
	}

	public RobotControlBean volumeDown(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("volumeDown");
	}

	public RobotControlBean volumeMin(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("volumeMin");
	}

	public RobotControlBean takePhoto(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("takePhoto");
	}

	public RobotControlBean queryPhoto(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("queryPhoto");
	}

	public RobotControlBean dance(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("dance");
	}

	public RobotControlBean volumeClose(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("volumeClose");
	}

	public RobotControlBean showText(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("showText");
	}

	public RobotControlBean closeShowText(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("closeShowText");
	}

	public RobotControlBean reboot(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("reboot");
	}

	public RobotControlBean recognizeFace(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("recognizeFace");
	}

	public RobotControlBean queryPower(YbnfCompileResult ybnfCompileResult, SemanticContext semanticContext) {
		return getResult("queryPower");
	}
}
