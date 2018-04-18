package com.newland.model.weixin.pojo;

/**
 * 复杂按钮(父按钮)
 * @author Sugar
 * @修改日期 2014-7-14上午10:27:27
 */
public class ComplexButton extends Button{
	private Button[] sub_button;
	private String type;
	private String url;
	private String key;
	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
