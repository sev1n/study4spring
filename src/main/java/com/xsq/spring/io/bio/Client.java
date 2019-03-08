package com.xsq.spring.io.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client {
	public static void send() {
		Random rand = new Random(47);
		while (true) {
			Socket s = null;
			OutputStream out = null;
			InputStream in = null;
			try {
				s = new Socket("127.0.0.1", ServerNormal.port);
				out = s.getOutputStream();
				char[] operators = { '+', '-', '*', '/' };
				String expression = String.valueOf(rand.nextInt(10)) + operators[rand.nextInt(4)]
						+ String.valueOf(rand.nextInt(10));
				System.out.println("expression : " + expression);
				out.write(expression.getBytes());
				
				in = s.getInputStream();
				System.out.println(in.read());
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception e) {

			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (Exception e) {
					}
				}
				if (null != out) {
					try {
						out.close();
					} catch (Exception e) {
					}
				}
				if (null != s) {
					try {
						s.close();
					} catch (Exception e) {
					}
				}

			}
		}
	}
	
	public static void main(String[] args) {
		Client.send();
	}
}
