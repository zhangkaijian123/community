package com.xuewen.community.controller;

import com.xuewen.community.dto.CommentDTO;
import com.xuewen.community.dto.QuestionDTO;
import com.xuewen.community.enums.CommentTypeEnum;
import com.xuewen.community.service.CommentService;
import com.xuewen.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/10 17:19
 **/
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model){

        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> commentDTO = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTO);
        return "question";
    }
}
