package com.xsq.spring.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * aio读写都是异步操作，完成后由操作系统回调进行后续操作
 * @author Administrator
 *
 */
public class AioServer {
	private AsynchronousServerSocketChannel serverChannel;
	private AtomicInteger connCount = new AtomicInteger(0);
	private CountDownLatch cdl = new CountDownLatch(1);

	private void start() throws IOException {
		serverChannel = AsynchronousServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(12345));
		new Thread(new Runnable() {

			@Override
			public void run() {
				serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

					@Override
					public void completed(AsynchronousSocketChannel channel, Object attachment) {
						System.out.println("链接的客户端数： " + connCount.incrementAndGet());
						serverChannel.accept(null, this);//支持多个链接
						ByteBuffer bf = ByteBuffer.allocate(1024);
						channel.read(bf, bf, new ReadHandler(channel));
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						System.out.println("客户端链接失败：" + exc.getMessage());
						cdl.countDown();
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

	public static void main(String[] args) throws IOException {
		AioServer server = new AioServer();
		server.start();
	}
	
	class ReadHandler implements CompletionHandler<Integer, ByteBuffer>{
		AsynchronousSocketChannel channel;
		public ReadHandler(AsynchronousSocketChannel channel) {
			super();
			this.channel = channel;
		}

		@Override
		public void completed(Integer result, ByteBuffer bf) {
			if (bf.hasRemaining()) {
				String cal = new String(bf.array(), 0, bf.limit(), Charset.forName("UTF-8"));
				System.out.println(cal);
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
				
				ByteBuffer bb = ByteBuffer.wrap(String.valueOf(value).getBytes());
				channel.write(bb, null, new CompletionHandler<Integer, Object>() {

					@Override
					public void completed(Integer result, Object attachment) {
						ByteBuffer b = ByteBuffer.allocate(1024);
						channel.read(b, b, new ReadHandler(channel));
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						// TODO Auto-generated method stub
					}
				});
			}
		}

		@Override
		public void failed(Throwable exc, ByteBuffer attachment) {
			System.out.println("服务端读取信息失败：" + exc.getMessage());
		}
	}
}
