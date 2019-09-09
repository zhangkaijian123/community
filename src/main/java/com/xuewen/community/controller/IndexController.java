package com.xuewen.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.dto.QuestionDTO;
import com.xuewen.community.mapper.QuestionMapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import com.xuewen.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author 张铠建
 * @description
 * @createdate 2019-04-24 16:12
 **/
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @Value("${pageSize}")
    private Integer size;

    @GetMapping(path = "/")
    public String welcomePage(HttpServletRequest request, Model model,
                                  @RequestParam(value = "page",defaultValue = "1") Integer page){
        //持久化登陆，拿到token
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("token",token);
                    User user = userMapper.selectOne(queryWrapper);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        Page<Question> ipage = new Page<>(page, size);
        PaginationDTO pagination = questionService.list(ipage);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
