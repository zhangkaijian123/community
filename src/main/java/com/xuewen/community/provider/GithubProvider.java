package com.xuewen.community.provider;

import com.alibaba.fastjson.JSON;
import com.xuewen.community.dto.AccessTokenDTO;
import com.xuewen.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:16
 **/
@Component
@Slf4j
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute()){
            try {

                String string = response.body().string();
                log.info("response:"+string);
                String[] split = string.split("&");
                String token = split[0].split("=")[1];
                log.info("token:"+token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            log.info("getUser response:"+string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            System.out.println("githubUser:"+githubUser);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
