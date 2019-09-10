package com.xuewen.community.exception;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 22:47
 **/
public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试？");
    private String message;

    @Override
    public String getMessage(){
        return message;
    }
    CustomizeErrorCode(String message){
        this.message = message;
    }
}
