package com.xuewen.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-12 00:29
 **/
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
