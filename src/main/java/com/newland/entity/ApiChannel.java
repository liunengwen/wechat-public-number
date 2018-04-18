package com.newland.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 接口渠道实体
 * @author fangxu.ge
 *
 */
@Entity
@Table(name = "t_api_channel")
public class ApiChannel implements Serializable{

	private static final long serialVersionUID = 2400080255730586645L;

	private String channelCode;  //渠道编码
	private String channelKey;  //渠道密钥
	private String remarks;  //备注
	
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelKey() {
		return channelKey;
	}
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
