package com.xsq.spring.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class AioClient{
	private AsynchronousSocketChannel channel;
	private CountDownLatch cdl = new CountDownLatch(1);
	
	private void start() throws IOException{
		 channel = AsynchronousSocketChannel.open();
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				 channel.connect(new InetSocketAddress("127.0.0.1", 12345), null, new CompletionHandler<Void, Object>() {
					@Override
					public void completed(Void result, Object attachment) {
						System.out.println("客户端成功连接到服务端。。。");
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						System.out.println("链接失败。。。。");
						try {
							channel.close();
							cdl.countDown();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				 try {
					cdl.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	private void sendMsg(String msg){
		ByteBuffer bf = ByteBuffer.wrap(msg.getBytes());
		channel.write(bf, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				ByteBuffer bf = ByteBuffer.allocate(1024);
				channel.read(bf, bf, new CompletionHandler<Integer, ByteBuffer>() {

					@Override
					public void completed(Integer result, ByteBuffer bf) {
						if (bf.hasRemaining()) {
							System.out.println(new String(bf.array(), 0, bf.limit(), Charset.forName("UTF-8")));
						}
						
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
						System.out.println("客户端读取数据失败:" + exc.getMessage());
						
					}
				});
				
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("数据发送失败：" + exc.getMessage());
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		AioClient client = new AioClient();
		client.start();	
		while(true){
			client.sendMsg(new Scanner(System.in).nextLine());
		}
	}
}
