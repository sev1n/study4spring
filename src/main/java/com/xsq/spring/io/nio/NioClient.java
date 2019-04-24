package com.xsq.spring.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NioClient {
	private Selector selector;
	private SocketChannel channel;
	
	{
		try {
			selector = Selector.open();
			channel = SocketChannel.open();
			channel.configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void start(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {	
					boolean conneted = channel.connect(new InetSocketAddress("127.0.0.1", 12345));
					if (!conneted) {
						channel.register(selector, SelectionKey.OP_CONNECT);
					}
					while(true){
						selector.select(3000);
						Set<SelectionKey> keys = selector.selectedKeys();
						Iterator<SelectionKey> it = keys.iterator();
						while(it.hasNext()){
							SelectionKey next = it.next();
							it.remove();
							proccess(next);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
	}
	
	private void proccess(SelectionKey key) throws IOException{
        if(key.isConnectable()){  
        	SocketChannel channel = (SocketChannel) key.channel();
    		System.out.println("connect : " + channel);
            if(channel.finishConnect());  
            else System.exit(1);  
        }  
		if (key.isReadable()) {
			ByteBuffer bf = ByteBuffer.allocate(1024);
			SocketChannel channel = (SocketChannel) key.channel();
			while(channel.read(bf) > 0){
				bf.flip();
				System.out.println(new String(bf.array(), 0, bf.limit(), Charset.forName("UTF-8")));
				bf.clear();
			}
		}
	}
	
	private void sendMsg(String msg) throws Exception{
		channel.register(selector, SelectionKey.OP_READ);
		ByteBuffer bf = ByteBuffer.wrap(msg.getBytes());
		channel.write(bf);
		System.out.println("sendMsg : " + channel);
	}
	
	public static void main(String[] args) throws Exception {
		NioClient client = new NioClient();
		client.start();
		while(true){
			client.sendMsg(new Scanner(System.in).nextLine());
		}
	}
}
