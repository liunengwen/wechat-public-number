package com.newland.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * 会员-获取用户openid
 * @author tl
 *
 */
public interface MemberInfoService {

	/**
	 * 会员-扫码获取用户openid
	 * @param request response
	 * @throws IOException
	 */
	public void getMemberInfoAndUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * 会员-公众号点击按钮时，获取用户openid
	 * @param request response
	 * @throws IOException
	 */
	public void getMemberUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	/**
	 * 会员-消费者收到券信息时，可以选择分享给他人
	 * @param request response
	 * @throws IOException
	 */
	public Map<String, Object> sendMemberShareCouponInfo(HttpServletRequest request, JSONObject json) throws IOException;
	
}
