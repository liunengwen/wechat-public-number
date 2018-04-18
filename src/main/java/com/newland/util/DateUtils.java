package com.newland.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * 
 * @author fangxu.ge
 *
 */
public class DateUtils {

	private static Logger log = LoggerFactory.getLogger(DateUtils.class);
			
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat timeMillisFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat timeSecondFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat shortMillisFormat = new SimpleDateFormat("yyMMddHHmmssSSS");

	/**
	 * 两日期相差多少秒
	 * 
	 * @return
	 */
	public static int secondsBetween(Date begin,Date end) {
		long between_seconds = (end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		return Integer.parseInt(String.valueOf(between_seconds));
	}
	
	/**
	 * 返回当前日期 日期格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getToday() {
		Calendar calendar = Calendar.getInstance();
		return dateToString(calendar.getTime(), "yyyyMMdd");
	}

	/**
	 * 返回当前日期时间 日期格式：yyyy-MM-dd hh:mm:ss
	 * 
	 * @return
	 */
	public static String getTime() {
		Calendar calendar = Calendar.getInstance();
		return dateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return date为空时返回空字符串
	 */
	public static String dateToString(Date date) {
		if (date != null) {
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 根据 pattern 规则格式化 日期
	 * 
	 * @param date
	 * @param pattern
	 * @return date为空时返回空字符串,pattern为空时返回默认格式("yyyy-MM-dd HH:mm:ss")
	 */
	public static String dateToString(Date date, String pattern) {
		if (!"".equals(pattern) && pattern != null) {
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			if (date != null) {
				return fmt.format(date);
			}
		}
		if (date != null) {
			return sdf.format(date);
		}
		return "";
	}

	public static Date dateFormat(Date date, String pattern) throws Exception {
		if (!"".equals(pattern) && pattern != null) {
			SimpleDateFormat fmt = new SimpleDateFormat(pattern);
			if (date != null) {
				String dd = fmt.format(date);
				return fmt.parse(dd);
			}
		}
		return null;
	}
	
	 /** 
     * 使用预设格式将字符串转为Date 
     */  
    public static Date parse(String strDate){
        try {
			return StringUtils.isBlank(strDate) ? null : sdf.parse(strDate);
		} catch (ParseException e) {
			log.error("=== DateUtils parse error：{}  ===",e.getMessage(),e);
		}
        return null;
    }  
    
    /** 
     * 日期类型装换  utc格式日期转date型
     */  
    public static Date parseDate(String strDate){
        try {
        	Date date = new Date();
        	if(StringUtils.isBlank(strDate)){
        		date = null;
        	}else{
        		strDate = strDate.replace("Z", " UTC");//注意是空格+UTC
        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
        		date = format.parse(strDate);
        		String activeDate = sdf.format(date);
        		date = sdf.parse(activeDate);		        
        	}
			return date;
		} catch (ParseException e) {
			log.error("=== DateUtils parse error：{}  ===",e.getMessage(),e);
		}
        return null;
    } 
  
    /** 
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern){
        try {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(  
			        pattern).parse(strDate);
		} catch (ParseException e) {
			log.error("=== DateUtils parse pattern error：{}  ===",e.getMessage(),e);
		}
        return null;
    }
	
	public static String getStringDateYMDHMSS() {
		return timeMillisFormat.format(Calendar.getInstance().getTime());
	}
	/**
	 * 年月日时分秒
	 * @return
	 */
	public static String getStringDateYMDHMS() {
		return timeSecondFormat.format(Calendar.getInstance().getTime());
	}
	public static String getDateSerialNo() {
		return shortMillisFormat.format(Calendar.getInstance().getTime());
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
   /** 
   *字符串的日期格式的计算 
   */  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }  
    /**
     * 获取当前时间格式yyyyMMddHHmmss
     * @return
     */
    public static String getTimes() {
		Calendar calendar = Calendar.getInstance();
		return dateToString(calendar.getTime(), "yyyyMMddHHmmss");
	}
    /**
     * 转换时间格式yyyyMMddHHmmss-yyyy-MM-dd HH:mm:ss
     * @param str
     * @return
     */
    public static Date getDate(String str){
    	String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
    	String dateStr = str.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
    	return parse(dateStr);
    }
    /**
     * 转换时间格式yyyy-MM-dd HH:mm:ss 为yyyy-MM-dd HH:mm
     * @param str
     * @return
     */
	public static String getDates(Date date){
		String dateStr = dateToString(date);
		return dateStr.substring(0, dateStr.length()-3);
	}
	/**
	 * 将yyyy-MM-dd转换为yyyy-MM-dd HH:mm:ss
	 * @param string
	 * @return
	 */
	public static Date changeDate(String string){
		StringBuffer str = new StringBuffer();
		str.append(string).append(" 00:00:00");
		return DateUtils.parse(str.toString(),"yyyy-MM-dd HH:mm:ss");
	}
}
