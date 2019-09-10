package com.xuewen.community.dto;

import com.xuewen.community.model.User;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/6 13:15
 **/
@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
    private String time;

    public void calculationTime(Long ctime) {
        String r = "";
        if(ctime==null)this.time=null;
        String format="yyyy-MM-dd HH:mm:ss";
        long nowtimelong = System.currentTimeMillis();
        long result = Math.abs(nowtimelong - ctime);
        if (result < 60000)// 一分钟内
        {
            long seconds = result / 1000;
            r = seconds + "秒钟前";
        } else if (result >= 60000 && result < 3600000)// 一小时内
        {
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000)// 一天内
        {
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else// 日期格式
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            r = sdf.format(new Date(ctime));
        }
        this.time = r;
    }
}
