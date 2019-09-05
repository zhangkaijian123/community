package com.xuewen.community.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:17
 **/

public class AccessTokenDTO {
    @Getter
    @Setter
    private String client_id;
    @Getter
    @Setter
    private String client_secret;
    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String redirect_uri;
    @Getter
    @Setter
    private String state;
}
