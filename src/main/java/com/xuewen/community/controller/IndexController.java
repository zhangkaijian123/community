package com.xuewen.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


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

    @GetMapping(path = "/")
    public String welcomePage(HttpServletRequest request){
        //持久化登陆，拿到token
        Cookie[] cookies = request.getCookies();
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

        return "index";
    }
}
