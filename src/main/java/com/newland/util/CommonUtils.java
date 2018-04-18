package com.newland.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.common.Constants;

/**
 * Description: 
 * @author fangxu.ge
 * @date 2017年8月9日 下午7:28:01
 */
public class CommonUtils {

	private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);
	
	/**
	 * UTF8编码
	 */
	public static String UTF8Encode(String originalStr){
		String encodeStr = null;
		if(StringUtils.isNotBlank(originalStr)){
			try {
				encodeStr = URLEncoder.encode(originalStr,Constants.CHARSET_UTF8);
			} catch (UnsupportedEncodingException e) {
				encodeStr = null;
				log.error("------ UTF8Encode error : {} ------",e);
			}
		}
		return encodeStr;
	}
	
	/**
	 * UTF8解码
	 */
	public static String UTF8Decode(String encodeStr){
		String decodeStr = null;
		if(StringUtils.isNotBlank(encodeStr)){
			try {
				decodeStr = URLDecoder.decode(encodeStr,Constants.CHARSET_UTF8);
			} catch (UnsupportedEncodingException e) {
				decodeStr = null;
				log.error("------ UTF8Decode error : {} ------",e);
			}
		}
		return decodeStr;
	}
}
