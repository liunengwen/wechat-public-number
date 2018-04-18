package com.newland.enums;
/**
 * 异常码
 * @author fangxu.ge
 * 
 */
public enum ExceptionCodeEnum {

    /** common 异常码 **/

	UNKNOWN("UNKNOWN", "未知异常"),

    EXCEPTION("EXCEPTION", "系统异常"),

	ERROR("ERROR", "系统错误"),

    BAD_REQUEST("BAD_REQUEST", "无效请求"),

    PARAM_NULL("PARAM_NULL", "输入参数为空"),
    
    PARAM_FORMAT_ERROR("PARAM_FORMAT_ERROR", "输入参数格式错误"),
    
    DATA_NOT_FOUNT("DATA_NOT_FOUNT", "查无数据"),
    
	SAVE_DATA_FAILED("SAVE_DATA_FAILED", "保存数据失败"),
	
	UPDATE_DATA_FAILED("UPDATE_DATA_FAILED", "更新数据失败"),
	
	DELETE_DATA_FAILED("DELETE_DATA_FAILED", "删除数据失败");
	
	
    /**
     * 根据编码找枚举
     * 
     * @param code 编码
     * @return
     */
    public static ExceptionCodeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }

        for (ExceptionCodeEnum t : values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

    private final String code;

    private final String label;

    private ExceptionCodeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public ExceptionCodeEnum returnEnum(String persistedValue) {
        return getByCode(persistedValue);
    }

}
