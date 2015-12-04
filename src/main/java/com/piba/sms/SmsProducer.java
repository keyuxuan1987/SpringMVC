package com.piba.sms;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings(value = "all")
/**
 *  Copyright (C), 2015年11月20日, winlu
 *  File name and path : piba.com.piba.sms.SmsProducer.java
 *  Author : ASUS,    Version : 1.0,    First complete date : 2015年11月20日 上午10:04:44
 *  Description :短信生产队列
 *  Others:
 *  Function List:
 *  History:
 *    1. Date:2015年11月20日 上午10:04:44
 *       Version:
 *       Author:kelvin
 *       Modification:
 *    
 **
 */
public class SmsProducer implements Runnable {

	private final BlockingQueue sharedQueue;

	public SmsProducer(BlockingQueue sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	public void run() {
		for (int i = 1; i < 10000; i++) {
			try {
				System.out.println("当前进去1 个人，还有 " + sharedQueue.size() +"个人。");
				sharedQueue.put(i);
			} catch (InterruptedException ex) {
				Logger.getLogger(SmsProducer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
