package com.xuewen.community.enums;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 10:58
 **/
public enum  NotificationStatusEnum {
    UNREAD(0),
    READ(1)
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
