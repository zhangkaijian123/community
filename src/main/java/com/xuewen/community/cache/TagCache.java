package com.xuewen.community.cache;

import com.xuewen.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        technology.setTags(Arrays.asList("java","javascript","php","css","html5","node.js","python","golang"));
        tagDTOS.add(technology);

        TagDTO complaints = new TagDTO();
        complaints.setCategoryName("吐槽");
        complaints.setTags(Arrays.asList("生活","闺蜜","基友"));
        tagDTOS.add(complaints);

        TagDTO campus = new TagDTO();
        campus.setCategoryName("校园");
        campus.setTags(Arrays.asList("老师","学校","食堂","同学","舍友"));
        tagDTOS.add(campus);

        TagDTO game = new TagDTO();
        game.setCategoryName("游戏");
        game.setTags(Arrays.asList("LOL","DNF","PUBG"));
        tagDTOS.add(game);
        return tagDTOS;
    }

    public static String filterInvalid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());

        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
