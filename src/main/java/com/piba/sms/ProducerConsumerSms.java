package com.piba.sms;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings(value = "all")
public class ProducerConsumerSms {
	public static void main(String args[]) {
		long start = System.currentTimeMillis();
		// Creating shared object
		BlockingQueue sharedQueue = new LinkedBlockingQueue();

		// Creating Producer and Consumer Thread
		Thread prodThread = new Thread(new SmsProducer(sharedQueue));
		Thread consThread = new Thread(new SmsConsumer(sharedQueue));

		// Starting producer and Consumer thread
		prodThread.start();
		consThread.start();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
