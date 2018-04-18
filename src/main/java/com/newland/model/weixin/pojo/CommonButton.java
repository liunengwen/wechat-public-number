package com.newland.model.weixin.pojo;
/**
 * 普通按钮（子按钮）
 * @author Sugar
 * @修改日期 2014-7-14上午10:26:21
 */
public class CommonButton extends Button{
	private String type;
	private String key;
	private String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
