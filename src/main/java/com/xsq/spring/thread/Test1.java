package com.xsq.spring.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 两个线程按次打印0~100的奇偶数
 * @author Administrator
 *
 */
public class Test1 {
	private CountDownLatch jishu = new CountDownLatch(1);
	private CountDownLatch oushu = new CountDownLatch(1);
	private int num = 0;
	
	private void printJishu() throws InterruptedException{
		while(num < 100){
			jishu.await();
			System.out.println("奇数线程： " + num++);
			oushu.countDown();
			jishu = new CountDownLatch(1);
		}
	}
	
	private void printOushu() throws InterruptedException{
		while(num < 100){
			oushu.await();
			System.out.println("偶数线程：" + num++);
			jishu.countDown();
			oushu = new CountDownLatch(1);
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Test1 t = new Test1();

		new Thread(new Runnable() {
			public void run() {
				try {
					t.printOushu();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "偶数线程").start();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					t.printJishu();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "奇数线程").start();
		
		t.oushu.countDown();
	}
}
