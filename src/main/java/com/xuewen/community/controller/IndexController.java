package com.xuewen.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.model.Question;
import com.xuewen.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



/**
 * @author 张铠建
 * @description
 * @createdate 2019-04-24 16:12
 **/
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private QuestionService questionService;

    @Value("${pageSize}")
    private Integer size;

    @GetMapping(path = "/")
    public String welcomePage(Model model,
                                  @RequestParam(value = "page",defaultValue = "1") Integer page){
        Page<Question> ipage = new Page<>(page, size);
        PaginationDTO pagination = questionService.list(ipage);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
