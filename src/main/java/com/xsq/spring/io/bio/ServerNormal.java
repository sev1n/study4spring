package com.xsq.spring.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNormal {

	public final static int port = 12345;
	private static ServerSocket serverSocket;
	
	public static void start() throws IOException{
		if (null == serverSocket) {
			serverSocket = new ServerSocket(port);
		}
		
		while(true){
			Socket socket = serverSocket.accept();
			new Thread(new Runnable() {
				
				@Override
				public void run() {

					InputStream in = null;
					OutputStream out = null;
					try {
						in = socket.getInputStream();
						byte[] b = new byte[1024];
						in.read(b);
						String expression = new String(b);
						System.out.println("接收到expression ：" + expression);
						String[] s = expression.replace(" ", "").split("");
						String operator = s[1];
						int value = 0;
						switch (operator) {
						case "+":
							value = Integer.valueOf(s[0]) + Integer.valueOf(s[2]);
							break;

						case "-":
							value = Integer.valueOf(s[0]) - Integer.valueOf(s[2]);
							break;
							
						case "*":
							value = Integer.valueOf(s[0]) * Integer.valueOf(s[2]);
							break;
							
						case "/":
							value = Integer.valueOf(s[0]) / Integer.valueOf(s[2]);
							break;
						}
						
						out = socket.getOutputStream();
						out.write(value);
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
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
						if (null != socket) {
							try {
								socket.close();
							} catch (Exception e) {
							}
						}
					}
				}
			}).start();
		}		
	}
	
	public static void main(String[] args) throws IOException {
		ServerNormal.start();
	}
}
