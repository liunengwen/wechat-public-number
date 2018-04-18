package com.newland.controller.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newland.service.InvoiceInfoService;
import com.newland.service.MarketInfoService;
import com.newland.service.MemberInfoService;
import com.newland.service.StagesInfoService;




/**
 * Description: 获取微信用户openid
 * @author tl
 * @date 2018年2月7日
 */
@RestController
@RequestMapping()
public class WeiXinController {
		
	@Autowired
	private InvoiceInfoService invoiceInfoService;
	@Autowired
	private StagesInfoService stagesInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MarketInfoService marketInfoService;
	
	/**
	 * 电子发票-扫码获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthQueryInfoServlet", method = RequestMethod.GET)
	public void getQrCodeUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		invoiceInfoService.getQrCodeUserOpenId(request, response);
	}
	
	/**
	 * 电子发票-公众号点击按钮后,获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthQueryServlet", method = RequestMethod.GET)
	public void getInvoiceUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		invoiceInfoService.getInvoiceUserOpenId(request, response);
	}
	
	/**
	 * 分期-扫码获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthStagesServlet", method = RequestMethod.GET)
	public void getOrderIdAndUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		stagesInfoService.getOrderIdAndUserOpenId(request, response);
	}
	
	/**
	 * 分期-公众号点击按钮后,获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthStagesInfoServlet", method = RequestMethod.GET)
	public void getStagesUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		stagesInfoService.getStagesUserOpenId(request, response);
	}
	
	/**
	 * 会员-扫码获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthMemberServlet", method = RequestMethod.GET)
	public void getMemberInfoAndUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		memberInfoService.getMemberInfoAndUserOpenId(request, response);
	}
	
	/**
	 * 会员-公众号点击按钮后,获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthMembersInfoServlet", method = RequestMethod.GET)
	public void getMemberUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		memberInfoService.getMemberUserOpenId(request, response);
	}
	
	/**
	 * 游戏中心-公众号点击按钮后,获取用户openid
	 * @param request response
	 * @throws IOException 
	 */
	@RequestMapping(value="/OAuthMarketInfoServlet", method = RequestMethod.GET)
	public void getMarketUserOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		marketInfoService.getMarketUserOpenId(request, response);
	}
	
}
