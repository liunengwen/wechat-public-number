package com.newland.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newland.enums.ExceptionCodeEnum;
import com.newland.exception.BaseException;
import com.newland.model.response.ResponseModel;

/**
 *
 * 功能： 通用异常处理和公共部分，所有controller都要继承这个类
 *
 */
public class BaseController {
	
	private  Logger log = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * @Description: 捕获BaseException异常处理
	 * @param e
	 * @return ResponseModel
	 */
    @ExceptionHandler(BaseException.class)
    public @ResponseBody ResponseModel baseExceptionHandler(Exception e) {
        // BaseException为业务错误
        log.error("业务处理异常,错误信息-[{}]", e.getMessage(),e);
        return new ResponseModel(ExceptionCodeEnum.EXCEPTION.getCode(),e.getMessage());
    }
    
    /**
	 * @Description: 捕获其他所有异常处理
	 * @param e
	 * @return ResponseModel
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseModel otherException(Exception e) {
        // Exception
        log.error("系统异常,错误信息-[{}]", e.getMessage(),e);
        return new ResponseModel(ExceptionCodeEnum.ERROR.getCode(),e.getMessage());
	}
	
}
