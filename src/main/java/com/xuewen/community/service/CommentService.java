package com.xuewen.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuewen.community.dto.CommentDTO;
import com.xuewen.community.dto.DianZanTDO;
import com.xuewen.community.enums.CommentTypeEnum;
import com.xuewen.community.exception.CustomizeErrorCode;
import com.xuewen.community.exception.CustomizeException;
import com.xuewen.community.mapper.CommentMapper;
import com.xuewen.community.mapper.QuestionMapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.Comment;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/11 9:05
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incComCommentCount(comment.getParentId());
        }else {
            //回复问题
            Question question = questionMapper.selectById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(comment.getParentId());
        }

    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        queryWrapper.eq("type", type.getType());
        queryWrapper.orderByDesc("comment_count");
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        if (comments.size()==0){
            return new ArrayList<>();
        }

        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<User> users = userMapper.selectBatchIds(commentators);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(commentDTO.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    public void dianzan(DianZanTDO dianZanTDO) {
        commentMapper.dianzan(dianZanTDO.getId(),dianZanTDO.getLikeCount());
    }
}
