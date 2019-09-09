package com.xuewen.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/9 12:43
 **/
@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action, Model model){

        if ("questions".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");

        }
        if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");

        }
        return "profile";
    }
}
