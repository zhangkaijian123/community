package com.xuewen.community.exception;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 22:39
 **/
public class CustomizeException extends RuntimeException{

    private String message;
    private Integer code;
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
