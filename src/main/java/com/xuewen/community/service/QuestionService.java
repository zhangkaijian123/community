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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.xuewen.community.Util.CalculationTimeUtil.calculationTime;

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

    public PaginationDTO list(String search, Page<Question> ipage) {

        if (StringUtils.isNotBlank(search)){
            String[] searchs = StringUtils.split(search, " ");
            search = Arrays.stream(searchs).collect(Collectors.joining("|"));
        }


        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        Integer page = (int)ipage.getCurrent();

        Integer totalCount = questionMapper.countBySearch(search);
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
        List<Question> questions = questionMapper.selectBySearch((page-1)*size,size,search);
         List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTO.setTime(calculationTime(question.getGmtCreate()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination(totalCount,(int)ipage.getCurrent(),size);
        return paginationDTO;
    }

    public PaginationDTO list(Page<Question> ipage, long id) {
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
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        for (Question question:questions) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTO.setTime(calculationTime(question.getGmtCreate()));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        paginationDTO.setPagination((int)iPage.getTotal(),(int)iPage.getCurrent(),(int)iPage.getSize());
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setTime(calculationTime(question.getGmtCreate()));
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

    public void incView(Long id) {
        questionMapper.incView(id);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }

        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        List<Question> questions = questionMapper.selectRelated(queryDTO.getId(),regexpTag);
        List<QuestionDTO> questionDTOS = questions.stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
