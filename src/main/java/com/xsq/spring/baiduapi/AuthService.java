package com.xsq.spring.baiduapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

/**
 * ��ȡtoken��
 */
public class AuthService {

	/**
	 * 获取权限token
	 * 
	 * @return 返回示例： { "access_token":
	 *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
	 *         "expires_in": 2592000 }
	 */
	public static String getAuth() {
		// 官网获取的 API Key 更新为你注册的
		String clientId = "Exvnxp2kKxEkVWGEG8I7uuR2";
		// 官网获取的 Secret Key 更新为你注册的
		String clientSecret = "81YcKtWFPR9jVyMorUOjN5GAVgo1fuhj";
		return getAuth(clientId, clientSecret);
	}

	/**
	 * 获取API访问token 该token有一定的有效期，需要自行管理，当失效时需重新获取.
	 * 
	 * @param ak
	 *            - 百度云官网获取的 API Key
	 * @param sk
	 *            - 百度云官网获取的 Securet Key
	 * @return assess_token 示例：
	 *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
	 */
	public static String getAuth(String ak, String sk) {
		// 获取token地址
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credentials"
				// 2. 官网获取的 API Key
				+ "&client_id=" + ak
				// 3. 官网获取的 Secret Key
				+ "&client_secret=" + sk;
		try {
			URL realUrl = new URL(getAccessTokenUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.err.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			/**
			 * 返回结果示例
			 */
			System.err.println("result:" + result);
			JSONObject jsonObject = new JSONObject(result);
			String access_token = jsonObject.getString("access_token");
			return access_token;
		} catch (Exception e) {
			System.err.printf("获取token失败！");
			e.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * logid	uint64	正确调用生成的唯一标识码，用于问题定位
	 *  result	object	包含审核结果详情
	 *	+spam	int	请求中是否包含违禁，0表示非违禁，1表示违禁，2表示建议人工复审
	 *	+reject	array	审核未通过的类别列表与详情
	 *	+review	array	待人工复审的类别列表与详情
	 *	+pass	array	审核通过的类别列表与详情
	 *	++label	int	请求中的违禁类型
	 *	++score	float	违禁检测分，范围0~1，数值从低到高代表风险程度的高低
	 *	++hit	array	违禁类型对应命中的违禁词集合，可能为空
	 *
	 *违禁labels类型说明
	 *1	暴恐违禁	默认开启，高级设置可选择关闭
	 *2	文本色情	默认开启，高级设置可选择关闭
	 *3	政治敏感	默认开启，高级设置可选择关闭
	 *4	恶意推广	默认开启，高级设置可选择关闭
	 *5	低俗辱骂	默认开启，高级设置可选择关闭
	 *6	低质灌水	默认关闭，高级设置可选择开启
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String filepath = "C:\\Users\\Administrator\\Desktop\\文本审核样本内容\\aaa.txt";
		String content = getContent(filepath);
		System.out.println("文本长度：" + content.length());
		int count = content.length() % 5000 == 0 ? content.length() / 5000 : content.length() / 5000 + 1;
		String url = "https://aip.baidubce.com/rest/2.0/antispam/v2/spam?access_token=" + getAuth();//10000次/天
		for (int i = 0; i < count; i++) {
			String post;//不超过20000字节
			if (i + 1 == count) {
				post = "content=" + URLEncoder.encode(content.substring(i * 5000), "UTF-8");
			} else {
				post = "content=" + URLEncoder.encode(content.substring(i * 5000, (i + 1) * 5000), "UTF-8");
			}

			URL realUrl;
			try {
				realUrl = new URL(url);
				// 打开和URL之间的连接
				HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
				// 发送请求参数
				printWriter.write(post);// post的参数 xx=xx&yy=yy
				// flush输出流的缓冲
				printWriter.flush();
				// 开始获取数据
				BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int len;
				byte[] arr = new byte[1024];
				while ((len = bis.read(arr)) != -1) {
					bos.write(arr, 0, len);
					bos.flush();
				}
				bos.close();
				String strs = bos.toString("utf-8");
				System.out.println(strs);
				/*
				 * JSONObject json = new JSONObject(strs);
				 * 
				 * if (json.getJSONObject("result").getInt("spam") > 0) {
				 * System.out.println(strs); }else{ System.out.println("没有违禁词");
				 * }
				 */
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static String getContent(String filepath) {
		StringBuilder content = new StringBuilder();
		FileChannel channel = null;
		try {
			channel = new FileInputStream(filepath).getChannel();
			ByteBuffer bf = ByteBuffer.allocate(1024);
			while (channel.read(bf) != -1) {
				bf.flip();
				content.append(new String(bf.array(), 0, bf.limit(), Charset.forName("UTF-8")));
				bf.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}
}
