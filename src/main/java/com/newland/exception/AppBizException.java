package com.newland.exception;


import java.io.Serializable;

import com.newland.enums.ExceptionCodeEnum;

/**
 * 公共异常类
 * @author fangxu.ge
 * @date 2017.6.3
 */
public class AppBizException extends BaseException implements Serializable {

	private static final long serialVersionUID = -385794830083937987L;
	
	/**
     * 异常码
     */
    protected ExceptionCodeEnum codeEnum;

    public AppBizException(ExceptionCodeEnum codeEnum, String msg, Throwable e) {
        super(codeEnum.getCode(), msg, e);
        this.codeEnum = codeEnum;
    }

    public AppBizException(ExceptionCodeEnum codeEnum, String msg) {
        super(codeEnum.getCode(), msg);
        this.codeEnum = codeEnum;
    }

    public ExceptionCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(ExceptionCodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }
}
