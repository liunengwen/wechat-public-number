package com.newland.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 分期-获取用户openid
 * @author tl
 *
 */
public interface StagesInfoService {

	/**
	 * 分期-扫码获取用户openid
	 * @param request response
	 * @throws IOException
	 */
	public void getOrderIdAndUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * 分期-公众号点击按钮时，获取用户openid
	 * @param request response
	 * @throws IOException
	 */
	public void getStagesUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
}
