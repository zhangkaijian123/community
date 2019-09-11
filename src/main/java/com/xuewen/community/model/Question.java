package com.xuewen.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 8:56
 **/
@Data
public class Question {

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
}
