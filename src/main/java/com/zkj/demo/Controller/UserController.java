package com.zkj.demo.Controller;

import com.zkj.demo.Entity.User;
import com.zkj.demo.Service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * @author 张铠建
 * @description
 * @createdate 2019-04-24 16:12
 **/
@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/")
    public String welcomePage(@RequestParam(name = "name",required = false,defaultValue = "World")String name1){
        return "index";
    }
}
