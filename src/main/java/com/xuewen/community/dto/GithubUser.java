package com.xuewen.community.dto;

import lombok.Data;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:44
 **/
@Data
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
