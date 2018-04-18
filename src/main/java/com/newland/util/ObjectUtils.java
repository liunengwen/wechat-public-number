package com.newland.util;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 描述：提供对象一些便利方法
 * 时间：2017-8-13
 * 作者：fangxu.ge
 */
public class ObjectUtils {

	private static Logger log = LoggerFactory.getLogger(ObjectUtils.class);
	
    private final static Map<String, Object> objMap = new HashMap<String, Object>();

    private ObjectUtils() {
    }

    /**
     * 实例化指定的类名，并返回转换后具体类型
     *
     * @param className 类名
     */
    public static Object newInstance(String className) {
        Object _result = objMap.get(className);
        if (_result != null)
            return _result;
        try {
            _result = Class.forName(className).newInstance();
            synchronized (className) {
                objMap.put(className, _result);
            }
        } catch (Exception ex) {
        }
        return _result;
    }

    /**
     * 转换对象为map
     *
     * @param o
     */
    public static Map convertObjectToMap(Object o) {
        Map _result = new CaseInsensitiveMap();
        if (o != null) {
            PropertyDescriptor[] _props = PropertyUtils.getPropertyDescriptors(o);
            for (int i = 0, length = _props.length; i < length; i++) {
                PropertyDescriptor _prop = _props[i];
                try {
                    Object v = PropertyUtils.getProperty(o, _prop.getName());
                    _result.put(_prop.getName(), v);
                } catch (Exception ex) {
                }
            }
        }
        return _result;
    }

    /**
     * 将对象转换成Map，属性名作为KEY
     *
     * @param o
     */
    public static Map toMap(Object o) {
        Map _result = new CaseInsensitiveMap();
        if (o != null) {
            PropertyDescriptor[] _props = PropertyUtils.getPropertyDescriptors(o);
            for (int i = 0, length = _props.length; i < length; i++) {
                PropertyDescriptor _prop = _props[i];
                try {
                    Object v = PropertyUtils.getProperty(o, _prop.getName());
                    _result.put(mapPropNameToFieldName(_prop.getName()), v);
                } catch (Exception ex) {
                }
            }
        }
        return _result;
    }

    /**
     * 将list转换成Map(注意KEY会被转换成string类型)
     *
     * @param list        对象集合
     * @param keyPropName 作为主键的属性
     */
    public static Map convertListToMap(List list, String keyPropName) {
        Map result = new HashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object object = (Object) it.next();
            if (object instanceof Map) {
                result.put(((Map) object).get(keyPropName).toString(), object);
            } else {
                try {
                    result.put(PropertyUtils.getProperty(object, keyPropName).toString(), object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 设置对象属性
     *
     * @param o    对象实例
     * @param name 属性名称
     * @param v    值
     */
    public static void setProperty(Object o, String name, Object v) {
        try {
            PropertyUtils.setProperty(o, name, v);
        } catch (Exception ex) {
            log.warn("setProperty failed!", ex);
        }
    }

    /**
     * 将属性名按照规则转换至字段名
     *
     * @param propName
     */
    private static String mapPropNameToFieldName(String propName) {
        List _words = new ArrayList();
        char[] _orig = propName.toCharArray();
        char[] _chars = propName.toUpperCase().toCharArray();
        int p = 0, i = 0, length = _orig.length;
        int count = 0;  // 代表不同的个数
        for (; i < length; i++) {
            if (_orig[i] == _chars[i]) {
                if (count > 0) {
                    _words.add(propName.substring(p, i));
                    count = 0;
                    p = i;
                }
            } else {
                count++;
            }
        }
        if (p != i) {
            _words.add(propName.substring(p, i));
        }
        return StringUtils.join(_words.toArray(), "_").toUpperCase();
    }

    /**
     * 将原始记录的map，按照数据库字段名和属性名的映射规则对key进行转换，并返回map
     *
     * @param map
     */
    private static Map convertMap(Map map) {
        Map _result = new CaseInsensitiveMap();
        for (Iterator _it = map.keySet().iterator(); _it.hasNext(); ) {
            String k = (String) _it.next();
            String _name = k.replace("_", "");
            _result.put(_name, map.get(k));
        }
        return _result;
    }
    
    /**
     * 将原对象的属性值copy到目标对象
     * @param dest 目标对象
     * @param orig 原对象
     */
    public static void copyProperties(Object dest ,Object orig)throws Exception {
    	PropertyUtils.copyProperties(dest, orig);
    }
    
}
