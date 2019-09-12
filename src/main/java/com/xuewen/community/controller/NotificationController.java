package com.xuewen.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.NotificationDTO;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.enums.NotificationTypeEnum;
import com.xuewen.community.model.Notification;
import com.xuewen.community.model.Question;
import com.xuewen.community.model.User;
import com.xuewen.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 12:55
 **/
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id")Long id, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if (user == null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
            ||NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
