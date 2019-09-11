package com.xuewen.community.dto;

import com.xuewen.community.model.User;
import lombok.Data;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/11 12:52
 **/
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentCount;
    private String content;
    private User user;
}
