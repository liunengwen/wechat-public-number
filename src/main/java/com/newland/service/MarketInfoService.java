package com.newland.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 游戏中心获取用户openid
 * @author tl
 *
 */
public interface MarketInfoService {

	/**
	 * 游戏中心-公众号点击按钮时，获取用户openid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getMarketUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
}
