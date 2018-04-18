package com.newland.util;

import java.util.Random;

/**
 * 字符串工具
 * 
 * @author: chenru
 * @date: 2016年10月21日下午08:56:21
 */
public class StrUtil {

    /**
     * 随机生成字母加字符串组合
     * 
     * @param length
     * @param upperCase
     * @return
     */
    public static String randomNumOrChar(Integer length, Boolean upperCase) {
        int count = 0;
        if (length != null) {
            count = length.intValue();
        }
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double d = (random.nextInt(2) % 2 == 0) ? (Math.random() * 9 + 49) : (Math.random() * 26 + 97);
            char c = (char) (int) (d);
            sb.append(c);
        }
        return (upperCase != null && upperCase) ? sb.toString().toUpperCase() : sb.toString();
    }

    /**
     * 随机生成数字
     * 
     * @param length
     * @return
     */
    public static String randomNum(Integer length) {
        int count = 0;
        if (length != null) {
            count = length.intValue();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            double d = Math.random() * 9 + 49;
            char c = (char) (int) (d);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 用于替换的字符
     */
    public static String SYMBOL = "*";

    /**
     * 字符串替换（如果头部位数加尾部位数大于字符串长度返回原字符串）
     * 
     * @param str
     *            被替换字符串
     * @param start
     *            字符串头部不被替换的位数，必须为非负整数
     * @param end
     *            字符串尾部不被替换的位数，必须为非负整数
     * @return 替换后的字符串
     * @throws Exception
     *             异常信息
     */
    public static String toConceal(String str, int start, int end) throws Exception {

        if (null == str || "".equals(str)) {
            return str;
        }

        if (start < 0 || end < 0) {
            return "";
        }

        int l = str.length();
        if (start + end >= l) {
            return str;
        }

        StringBuffer sb = new StringBuffer(l);
        sb.append(str.substring(0, start));
        for (int i = 0; i < (l - start - end); i++) {
            sb.append(SYMBOL);
        }
        sb.append(str.substring(l - end));

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(StrUtil.randomNumOrChar(6, true));
        }
    }

}