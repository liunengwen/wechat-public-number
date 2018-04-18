package com.newland.model.response;

import java.io.Serializable;

/**
 * Response 公共返回实体
 * @author fangxu.ge
 *
 */
public class ResponseModel implements Serializable{
	
	private static final long serialVersionUID = 5700536698833222611L;
	
	private String code; //返回结果码
	private String message; //返回结果信息
	private Object data; //返回数据内容
	
	public ResponseModel(){
	}
	
	public ResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }
	
	public ResponseModel(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
