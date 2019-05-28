package com.zkj.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:44
 **/
public class GithubUser {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String bio;
}
