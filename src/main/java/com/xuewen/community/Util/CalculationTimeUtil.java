package com.xuewen.community.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 11:17
 **/
public class CalculationTimeUtil {

    public static String calculationTime(Long ctime) {
        String r = "";
        if(ctime==null)return null;
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
        return r;
    }
}
