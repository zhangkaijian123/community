package com.xuewen.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.PaginationDTO;
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

    public PaginationDTO list(Page<Question> ipage) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        IPage<Question> iPage = questionMapper.selectPage(ipage,queryWrapper);
        List<Question> questions = iPage.getRecords();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination((int)iPage.getTotal(),(int)iPage.getCurrent(),(int)iPage.getSize());
        return paginationDTO;
    }
}
