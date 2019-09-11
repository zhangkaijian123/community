package com.xuewen.community.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-05-27 13:17
 **/
@Data
public class AccessTokenDTO {

    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;
}
