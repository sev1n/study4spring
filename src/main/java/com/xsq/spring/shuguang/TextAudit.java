package com.xsq.spring.shuguang;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class TextAudit {
	private static final int SIZE = 1024;
	private static final String URL_BEIJING = "http://api.fengkongcloud.com/v2/saas/anti_fraud/text";
	private static final String URL_SHENZHEN = "http://api-sz.fengkongcloud.com/v2/saas/anti_fraud/text";
	private static final String URL_SHANGHAI = "http://api-sh.fengkongcloud.com/v2/saas/anti_fraud/text";
	private static final String URL_XJP = "http://api-xjp.fengkongcloud.com/v2/saas/anti_fraud/text";

	public static void main(String[] args) {
		RequestBody requestBody = new RequestBody();
		RequestBody.TextData data = requestBody.new TextData(getText("filePath"), "tokenId");
		requestBody.setAccessKey("accessKey");
		requestBody.setType("NEWS");
		requestBody.setData(data);

		String result = sendPostRequest(URL_SHANGHAI, JSON.toJSONString(requestBody));
	}

	public static String sendPostRequest(String url, String requestBody) {
		String result = null;
		CloseableHttpResponse response = null;
		try {

			HttpPost httpPost = new HttpPost(url);
			// 构建消息实体
			StringEntity entity = new StringEntity(requestBody, Charset.forName("UTF-8"));
			httpPost.setEntity(entity);

			RequestConfig config = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(120000).build();

			httpPost.setConfig(config);
			// 支持json交互
			httpPost.setHeader("content-type", "application/json");
			response = HttpClients.createDefault().execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity httpEntity = response.getEntity();
			result = null;
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity, "UTF-8");
			}
			EntityUtils.consume(httpEntity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@SuppressWarnings("resource")
	public static String getText(String filePath) {
		StringBuilder text = new StringBuilder();
		FileChannel channel = null;
		try {
			channel = new FileInputStream(filePath).getChannel();
			ByteBuffer bf = ByteBuffer.allocate(SIZE);
			while (channel.read(bf) != -1) {
				bf.flip();
				text.append(new String(bf.array(), 0, bf.limit(), Charset.forName("UTF-8")));
				bf.clear();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != channel) {
				try {
					channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return text.toString();
	}
}
