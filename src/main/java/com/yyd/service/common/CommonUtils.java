package com.yyd.service.common;

import java.util.Random;

import com.yyd.service.common.Number;

public class CommonUtils {

	public static int randomInt(int bound) {
		Random rd = new Random();
		return rd.nextInt(bound);
	}

	public static Number strToNumber(String numberStr) {
		return new Number(numberStr);
	}
}
