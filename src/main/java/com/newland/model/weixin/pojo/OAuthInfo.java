package com.newland.model.weixin.pojo;


public class OAuthInfo {
	
	//网页授权接口调用凭证
	private String accessToken;
	
	//access_token接口调用凭证超时时间
	private int expiresIn;
	
	//用户刷新access_token
	private String refreshToken;
	
	//用户唯一标识
	private String openId;
	
	//用户授权的作用域
	private String scope;
	
	public String getAccessToken() {
		return accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public String getOpenId() {
		return openId;
	}
	public String getScope() {
		return scope;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
