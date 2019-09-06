package com.xuewen.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuewen.community.mapper.QuestionMapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/5 18:58
 **/
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (StringUtils.isBlank(title)){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (title.length()>25){
            model.addAttribute("error", "标题不能超过25字");
            return "publish";
        }
        if (StringUtils.isBlank(description)){
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }


        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("token",token);
                    user = userMapper.selectOne(queryWrapper);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        if (user==null){
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insert(question);
        return "redirect:/";
    }
}
