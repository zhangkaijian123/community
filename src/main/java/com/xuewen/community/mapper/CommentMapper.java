package com.xuewen.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuewen.community.model.Comment;
import org.apache.ibatis.annotations.Update;

public interface CommentMapper extends BaseMapper<Comment> {

    @Update("update comment set like_count=like_count+#{likeCount} where id=#{id}")
    int dianzan(Long id,Integer likeCount);
}
