package com.xuewen.community.dto;

import lombok.Data;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 23:54
 **/
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
