package com.xuewen.community.dto;

import com.xuewen.community.model.User;
import lombok.Data;


/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 11:13
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String time;
    private Integer type;
    private String typeName;
}
