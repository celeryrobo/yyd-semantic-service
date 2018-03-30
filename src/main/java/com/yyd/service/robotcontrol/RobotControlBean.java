package com.yyd.service.robotcontrol;

import com.ybnf.compiler.beans.AbstractSemanticResult;

public class RobotControlBean extends AbstractSemanticResult {
	private String text;
	private String command;

	public RobotControlBean() {
		setOperation(Operation.COMMAND);
		setParamType(ParamType.TC);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
