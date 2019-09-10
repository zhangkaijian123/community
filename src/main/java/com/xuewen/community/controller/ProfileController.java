package com.xuewen.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import com.xuewen.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/9 12:43
 **/
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Value("${pageSize}")
    private Integer size;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action, Model model, @RequestParam(value = "page",defaultValue = "1") Integer page, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");

        }
        if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

        }

        Page<Question> ipage = new Page<>(page, size);
        PaginationDTO pagination = questionService.list(ipage,user.getId());
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
