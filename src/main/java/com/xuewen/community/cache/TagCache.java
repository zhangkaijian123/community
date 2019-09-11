package com.xuewen.community.cache;

import com.xuewen.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-12 00:29
 **/
public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO technology = new TagDTO();
        technology.setCategoryName("技术");
        technology.setTags(Arrays.asList("java","javascript","php","css","html5","node.js","python","c++","c","golang"));
        tagDTOS.add(technology);
        return tagDTOS;
    }
}
