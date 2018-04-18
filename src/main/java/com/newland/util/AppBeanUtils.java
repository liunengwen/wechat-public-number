package com.newland.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * bean工具类
 * 
 * @author: fangxu.ge
 * 
 */
public class AppBeanUtils {

	/**
	 * 获取bean 属性，bean为空时返回null
	 * @param bean 
	 * @param name  bean property
	 * @return
	 */
	public static String getSimpleProperty(Object bean , String name) {
		if(bean == null){
			return null;
		}else{
			try {
				return BeanUtils.getSimpleProperty(bean, name);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 判断bean 属性是否为空，bean或属性为空时返回false
	 * @param bean 
	 * @param fields  fields of bean
	 * @return
	 */
	public static boolean valuesAreNotEmpty(Object bean ,String... fields) {
		boolean result = true;
		if (bean == null || fields == null || fields.length == 0) {
			result = false;
		} else {
			for (String value : fields) {
				result &= !isEmpty(bean, value);
			}
		}
		return result;
	}
	
	/**
	 * 判断bean 属性为空
	 * @param bean 
	 * @param name  field of bean
	 * @return
	 */
	public static boolean isEmpty(Object bean , String name) {
		String value = null;
		try {
			value = BeanUtils.getProperty(bean, name);
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		if (StringUtils.isBlank(value)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断JSONObject 属性是否为空，json或属性为空时返回false
	 * @param json 
	 * @param keys  keys of json
	 * @return
	 */
	public static boolean jsonValuesAreNotEmpty(JSONObject json ,String... keys) {
		if (json == null || keys == null || keys.length == 0) {
			return false;
		} else {
			for (String key : keys) {
				//json.getString(key) ali json本质是map，无值时返回null
				if (StringUtils.isBlank(json.getString(key))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 判断JSONObject 属性是否为空，json或属性为空时返回为空的参数提示
	 * 全部验证通过时返回null
	 * @param json 
	 * @param keys  keys of json
	 * @return
	 */
	public static String validateJsonValue(JSONObject json ,String... keys) {
		String resultMsg = null;
		if (json == null || keys == null || keys.length == 0) {
			return "参数为空";
		} else {
			List<String> keyList = new ArrayList<String>();
			for (String key : keys) {
				//json.getString(key) ali json本质是map，无值时返回null
				if (StringUtils.isBlank(json.getString(key))) {
					keyList.add(key);
				}
			}
			if(keyList.size()>0){
				resultMsg = "参数" + StringUtils.join(keyList.toArray(), "、") + "不能为空";
			}
		}
		return resultMsg;
	}
	
	/**
	 * 判断JSONObject对象是否为空，json为空时返回为空的参数提示
	 * 全部验证通过时返回null
	 * @param json 
	 * @param keys  keys of json
	 * @return
	 */
	public static String validateDataValue(Integer line,Map<String,Object> map,String... keys) {
		String resultMsg = null;
		if ( keys == null || keys.length == 0) {
			return "参数为空";
		} else {
			List<String> keyList = new ArrayList<String>();
			for (String key : keys) {
				if (StringUtils.isBlank((String)map.get(key))) {
					keyList.add(key);
				}
			}
			if(keyList.size()>0){
				resultMsg = "第"+line+"条发票信息，参数" + StringUtils.join(keyList.toArray(), "、") + "不能为空";
			}
		}
		return resultMsg;
	}
}