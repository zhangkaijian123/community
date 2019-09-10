package com.xuewen.community.model;

import lombok.Data;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 23:56
 **/
@Data
public class Comment {

    private Integer id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
}
