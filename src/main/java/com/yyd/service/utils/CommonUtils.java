package com.yyd.service.utils;

import java.util.Random;

import com.yyd.service.utils.Number;

public class CommonUtils {

	public static int randomInt(int bound) {
		Random rd = new Random();
		return rd.nextInt(bound);
	}

	public static Number strToNumber(String numberStr) {
		return new Number(numberStr);
	}
}
