package com.xuewen.community.dto;

import com.xuewen.community.model.User;
import lombok.Data;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 13:15
 **/
@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
