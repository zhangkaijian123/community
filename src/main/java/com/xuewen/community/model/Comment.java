package com.xuewen.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 23:56
 **/
@Data
public class Comment {

    @TableId(value = "id",type = IdType.INPUT)
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long commentCount;
    private Long likeCount;
    private String content;
}
