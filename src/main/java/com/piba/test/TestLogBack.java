package com.piba.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogBack {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(TestLogBack.class);
		logger.debug("日志记录debug");
		logger.info("日志记录info");
		logger.error("日志记录error");
		String sign = "750b5d5ac4e6bd799f98189ed9d0238f";
		System.out.println(sign.length());
	}
}
