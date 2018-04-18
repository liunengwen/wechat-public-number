package com.newland.model.weixin.util;

import java.net.URLEncoder;

import com.newland.service.PropertyInfoService;



public class GetWeiXinCode {
	public static String  GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public static String getCodeRequest(){
        String result = null;
        GetCodeRequest  = GetCodeRequest.replace("APPID", urlEnodeUTF8(PropertyInfoService.getAppId()));
        GetCodeRequest  = GetCodeRequest.replace("REDIRECT_URI",urlEnodeUTF8(Constants.AOUTH_URL));
        GetCodeRequest = GetCodeRequest.replace("SCOPE", Constants.SCOPE);
        result = GetCodeRequest;
        return result;
    }
    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(getCodeRequest());
    }
}
