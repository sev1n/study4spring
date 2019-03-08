package com.xsq.spring.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
	private static final int PORT = 12345;
	private static final int SIZE = 1024;
	ServerSocketChannel channel = null;;
	Selector selector = null;

	public void start() throws Exception{
		selector = Selector.open();
		channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(PORT), 100);
		channel.register(selector, SelectionKey.OP_ACCEPT);

		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						selector.select(10000);
						Set<SelectionKey> keys = selector.selectedKeys();//已经就绪的channel
						Iterator<SelectionKey> it = keys.iterator();
						while(it.hasNext()){
							SelectionKey key = it.next();
							it.remove();
							process(key);
						}
						//while
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private void process(SelectionKey key) throws IOException {
		if (key.isAcceptable()) {
			ServerSocketChannel sc = (ServerSocketChannel) key.channel();
			SocketChannel socket = sc.accept();
			socket.configureBlocking(false);
			socket.register(selector, SelectionKey.OP_READ);
		}
		if (key.isReadable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			ByteBuffer bb = ByteBuffer.allocate(SIZE);
			while(channel.read(bb) != -1){
				bb.flip();//明确数据长度
				System.out.println(new String(bb.array()));
				bb.clear();//指针复位
			}
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		NioServer server = new NioServer();
		server.start();
	}
}
