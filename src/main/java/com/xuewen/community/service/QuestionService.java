package com.xuewen.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.dto.QuestionDTO;
import com.xuewen.community.exception.CustomizeErrorCode;
import com.xuewen.community.exception.CustomizeException;
import com.xuewen.community.mapper.QuestionMapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        Integer page = (int)ipage.getCurrent();

        Integer totalCount = questionMapper.selectCount(queryWrapper);
        Integer totalPage = 0;
        Integer size = (int)ipage.getSize();
        if (totalCount % size ==0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if (page > totalPage){
            ipage.setCurrent(totalPage);
        }
        IPage<Question> iPage = questionMapper.selectPage(ipage,queryWrapper);
        List<Question> questions = iPage.getRecords();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTO.calculationTime(question.getGmtCreate());
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination((int)iPage.getTotal(),(int)iPage.getCurrent(),(int)iPage.getSize());
        return paginationDTO;
    }

    public PaginationDTO list(Page<Question> ipage, Integer id) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creator",id);
        Integer page = (int)ipage.getCurrent();

        Integer totalCount = questionMapper.selectCount(queryWrapper);
        Integer totalPage = 0;
        Integer size = (int)ipage.getSize();
        if (totalCount % size ==0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if (page > totalPage){
            ipage.setCurrent(totalPage);
        }
        IPage<Question> iPage = questionMapper.selectPage(ipage,queryWrapper);
        List<Question> questions = iPage.getRecords();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTO.calculationTime(question.getGmtCreate());
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination((int)iPage.getTotal(),(int)iPage.getCurrent(),(int)iPage.getSize());
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.calculationTime(question.getGmtCreate());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void creatOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            question.setGmtModified(question.getGmtCreate());
            int updated = questionMapper.updateById(question);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        questionMapper.incView(id);
    }
}
