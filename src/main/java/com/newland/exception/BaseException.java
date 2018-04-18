package com.newland.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    private String code; // 异常码

    private Throwable cause;

    private Object[] params; // 其他参数

    public BaseException(String code, String msg) {
        super(msg);
        this.setCause(null);
    }

    public BaseException(String code, String msg, Throwable e) {
        this(code, msg);
        this.setCause(e);
    }

    public BaseException(String code, Object[] params, String msg) {
        this(code, msg);
        this.params = params;
    }

    public BaseException(String code, Object[] params, String msg, Throwable e) {
        this(code, msg, e);
        this.params = params;
    }

    public Object[] getParams() {
        return this.params;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

}
