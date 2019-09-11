package com.xuewen.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuewen.community.model.Question;
import org.apache.ibatis.annotations.Update;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 8:56
 **/
public interface QuestionMapper extends BaseMapper<Question> {
    @Update("update question set view_count = view_count + 1 where id = #{id}")
    int incView(Long id);

    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    int incCommentCount(Long id);

    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    int incComCommentCount(Long id);
}
