package com.newland.controller.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.newland.service.MemberInfoService;

/**
 * Description: 分享朋友圈等配置
 * @author tl
 * @date 2018年3月14日
 */
@RestController
@RequestMapping()
public class WXShareConfigController {
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	/**
	 * 会员-券信息分享给朋友
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/MemberShareCouponInfo", method = RequestMethod.POST)
	public Map<String, Object> getMarketUserOpenId(HttpServletRequest request, @RequestBody JSONObject inParam) throws IOException{
		return memberInfoService.sendMemberShareCouponInfo(request, inParam);
	}

}
