package com.newland.enums;
/**
 * 状态枚举
 * @author fangxu.ge
 * 
 */
public enum StatusEnum {
	
	/** 1-有效 */
    VALID("1", "有效"),

    /** 2-无效 */
    INVALID("2", "无效"),

    /** 3-删除 */
    DELETED("3", "删除");
    
    /**
     * 根据编码找枚举
     * 
     * @param code 编码
     * @return
     */
    public static StatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        if (values() == null) {
            return null;
        }
        for (StatusEnum t : values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        return null;
    }

    private final String code;

    private final String label;

    private StatusEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public StatusEnum returnEnum(Integer persistedValue) {
        return getByCode(persistedValue);
    }

}