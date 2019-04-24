package com.xsq.spring.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 学习地址：https://blog.csdn.net/caohongshuang/article/details/79455391
 * nio服务端轮询客户端就绪请求进行处理。
 * @author Administrator
 *
 */
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
			System.out.println("acceptable " + socket);
			socket.configureBlocking(false);
			socket.register(selector, SelectionKey.OP_READ);
		}
		if (key.isReadable()) {
			SocketChannel channel = (SocketChannel) key.channel();
			System.out.println("readable " + channel);
			ByteBuffer bb = ByteBuffer.allocate(SIZE);
			String cal = null;
			while(channel.read(bb) > 0){
				bb.flip();//明确数据长度
				cal = new String(bb.array(), 0, bb.limit(), Charset.forName("UTF-8"));
				System.out.println(cal);
				bb.clear();//指针复位
			}
			String[] s = cal.replace(" ", "").split("");
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
			channel.write(ByteBuffer.wrap(String.valueOf(value).getBytes()));
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		NioServer server = new NioServer();
		server.start();
	}
}
