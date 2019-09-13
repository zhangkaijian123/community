package com.xuewen.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuewen.community.Util.AesException;
import com.xuewen.community.dto.AccessTokenDTO;
import com.xuewen.community.dto.GithubUser;
import com.xuewen.community.mapper.UserMapper;
import com.xuewen.community.model.User;
import com.xuewen.community.provider.GithubProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import com.xuewen.community.Util.SHA1;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:34
 **/
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser !=null && githubUser.getId() != null){
            String token = UUID.randomUUID().toString();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("account_id",githubUser.getId());
            //查看数据库中是否存在此用户
            User isHasUser = userMapper.selectOne(queryWrapper);
            if (isHasUser!=null){
                //如果存在，更新用户的token
                isHasUser.setToken(token);
                userMapper.updateById(isHasUser);
                request.getSession().setAttribute("user",isHasUser);
            }else {
                //如果不存在，插入新用户
                User user = new User();
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userMapper.insert(user);
                request.getSession().setAttribute("user",user);
            }

            //登陆成功，写Session和Cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败，重新登录
            log.error("callback get github error,{}",githubUser);
            return "redirect:/";
        }

    }

    @GetMapping("/loginout")
    public String loginOUt(HttpServletRequest request,HttpServletResponse response){
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.getSession().removeAttribute("user");
            Cookie newCookie = new Cookie("token", null);//cookie名字要相同
            response.addCookie(newCookie);
        }
        return "redirect:/";
    }

    @GetMapping("/wechat")
    @ResponseBody
    public String wehcat(@RequestParam("signature") String signature,@RequestParam("timestamp")String timestamp,@RequestParam("nonce")String nonce,@RequestParam("echostr")String echostr){
        String token="somelog";//这里填基本配置中的token
        String jiami="";
        try {
            jiami=SHA1.getSHA1(token, timestamp, nonce,"");//这里是对三个参数进行加密
        } catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (jiami.equals(signature)){
            return echostr;
        }
        return null;
    }
}
