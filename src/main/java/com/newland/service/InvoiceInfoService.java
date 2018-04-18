package com.newland.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 电子发票扫描二维码获取用户openid
 * @author lx
 *
 */
public interface InvoiceInfoService {

	/**
	 * 电子发票-扫码获取用户openid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getQrCodeUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * 电子发票-公众号点击按钮时，获取用户openid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getInvoiceUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
}
