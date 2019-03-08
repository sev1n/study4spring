package com.xsq.spring.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioClient {
	
	private void start() throws Exception{
		Selector selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress("127.0.0.1", 12345));
		channel.register(selector, SelectionKey.OP_CONNECT);
	}
	
	public static void main(String[] args) throws Exception {
		new Thread(() -> {
			try {
				Selector selector = Selector.open();
				SocketChannel channel = SocketChannel.open();
				channel.configureBlocking(false);
				channel.connect(new InetSocketAddress("127.0.0.1", 12345));
				System.out.println(selector);
				channel.register(selector, SelectionKey.OP_CONNECT);
				while (true) {
					int i = 0;
					ByteBuffer bb = ByteBuffer.wrap(("今天是星期：" + (++i)).getBytes());
					channel.write(bb);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}
