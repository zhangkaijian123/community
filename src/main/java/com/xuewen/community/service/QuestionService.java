package com.xuewen.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuewen.community.dto.QuestionDTO;
import com.xuewen.community.mapper.QuestionMapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 13:16
 **/
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        List<Question> questions = questionMapper.selectList(queryWrapper);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
