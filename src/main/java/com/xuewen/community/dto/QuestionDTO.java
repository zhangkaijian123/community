package com.xuewen.community.dto;

import com.xuewen.community.model.User;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 13:15
 **/
@Data
public class QuestionDTO {

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
    private User user;
    private String time;

}
