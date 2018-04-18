package com.newland.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 返回数据处理
 * @author fangxu.ge
 *
 */
@ControllerAdvice(basePackages = "com.newland.controller")
public class CustomResponseBodyAdvice implements ResponseBodyAdvice {
	
	private static final Logger log = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);
	
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;// 返回true 表示继续执行下去
	}

	/**
	 * request请求结果返回前处理方法
	 */
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType, Class selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		//body 是请求返回的数据
		log.info("=== beforeBodyWrite return body ：{} ===",JSONObject.toJSONString(body,SerializerFeature.WriteMapNullValue));
		return body;
	}
	
}
