package com.xsq.spring.shuguang;

import java.io.Serializable;

public class RequestBody implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String accessKey;//用于权限认证
	/*
	 * 平台业务类型，可选值：ZHIBO：直播
	 *  ECOM：电商
	 *  GAME：游戏
	 *  NEWS：新闻资讯
	 *  FORUM：论坛
	 *  SOCIAL：社交
	 */
	private String type;
	private TextData data;//请求数据内容，最长1MB
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TextData getData() {
		return data;
	}

	public void setData(TextData data) {
		this.data = data;
	}

	public RequestBody(String accessKey, String type, TextData data) {
		super();
		this.accessKey = accessKey;
		this.type = type;
		this.data = data;
	}

	public RequestBody() {
		super();
		// TODO Auto-generated constructor stub
	}

	class TextData{
		private String text;//要检测的文本内容
		/*
		 * 客户端用户账号唯一标识，用于用户行为分析，建议传入用户UID
		 * 注：不同用户务必传入不同的tokenId以对其进行唯一标识
		 */
		private String tokenId;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getTokenId() {
			return tokenId;
		}
		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}
		public TextData(String text, String tokenId) {
			super();
			this.text = text;
			this.tokenId = tokenId;
		}
		public TextData() {
			super();
			// TODO Auto-generated constructor stub
		}
	}
}
