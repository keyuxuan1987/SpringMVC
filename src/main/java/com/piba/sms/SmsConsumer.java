package com.piba.sms;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Copyright (C), 2015年11月20日, winlu
 *  File name and path : piba.com.piba.sms.SmsConsumer.java
 *  Author : ASUS,    Version : 1.0,    First complete date : 2015年11月20日 上午10:05:56
 *  Description :短信消费队列
 *  Others:
 *  Function List:
 *  History:
 *    1. Date:2015年11月20日 上午10:05:56
 *       Version:
 *       Author:kelvin
 *       Modification:
 *    
 **
 */
@SuppressWarnings(value = "all")
public class SmsConsumer implements Runnable {
	private final BlockingQueue sharedQueue;

	public SmsConsumer(BlockingQueue sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	public void run() {
		while (true) {
			try {
				System.out.println("当前出去: " + sharedQueue.take() + " 个人，还有 " + sharedQueue.size() +"个人。");
			} catch (InterruptedException ex) {
				Logger.getLogger(SmsConsumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
