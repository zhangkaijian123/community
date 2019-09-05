package com.xuewen.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/5 16:36
 **/
@Data
@TableName(value = "user")
public class User {

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
}
