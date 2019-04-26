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
    /**
     * @description 注册用户
     * @author 张铠建
     * @date 2019/4/24
     * @param name 用户姓名
     * @param password 用户密码
     * @param email
     * @param user
     * @return java.lang.String
     */
    @PostMapping(value = "/add")
    public String addUser(@RequestParam String name,@RequestParam String password,@RequestParam String email, User user){
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "index";
    }
    /**
     * @description 登录功能
     * @author 张铠建
     * @date 2019/4/24
     * @param email
     * @param password
     * @param model
     * @return java.lang.String
     */
    @PostMapping(value = "/login")
    public String login(@RequestParam String email,@RequestParam String password, Model model){
        User user = userRepository.findByEmail(email);
        //如果未查到该用户
        if (user==null){
            log.warn("未查到该用户");
            return "该用户不存在";
        }else {
            if (user.getPassword().equals(password)){
                //登陆成功
                model.addAttribute("name",user.getName());
                log.warn("登陆成功");
            }else {
                model.addAttribute("name","登录失败！");
                log.warn("登录失败");
            }
        }
        return "index";
    }
    /**
     * @description 查看所有用户
     * @author 张铠建
     * @date 2019/4/24
     * @param
     * @return java.lang.Iterable<com.zkj.demo.Entity.User>
     */
    @ResponseBody
    @GetMapping(value = "/all")
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping(path = "/")
    public String welcomePage(@RequestParam(name = "name",required = false,defaultValue = "World")String name1){
        return "index";
    }
}
