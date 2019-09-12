package com.xuewen.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuewen.community.dto.NotificationDTO;
import com.xuewen.community.dto.PaginationDTO;
import com.xuewen.community.enums.NotificationStatusEnum;
import com.xuewen.community.enums.NotificationTypeEnum;
import com.xuewen.community.exception.CustomizeErrorCode;
import com.xuewen.community.exception.CustomizeException;
import com.xuewen.community.mapper.NotificationMapper;
import com.xuewen.community.model.Notification;
import com.xuewen.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @Description TODO
 * @Author 张铠建
 * @Date 2019/9/12 11:21
 **/
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;


    public PaginationDTO list(Page<Notification> ipage, Long id) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",id);
        queryWrapper.orderByAsc("status");
        Integer page = (int)ipage.getCurrent();

        Integer totalCount = notificationMapper.selectCount(queryWrapper);
        Integer totalPage = 0;
        Integer size = (int)ipage.getSize();
        if (totalCount % size ==0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if (page > totalPage){
            ipage.setCurrent(totalPage);
        }
        IPage<Notification> iPage = notificationMapper.selectPage(ipage,queryWrapper);
        List<Notification> notifications = iPage.getRecords();

        if (notifications.size() == 0){
            return paginationDTO;
        }
        
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        paginationDTO.setPagination((int)iPage.getTotal(),(int)iPage.getCurrent(),(int)iPage.getSize());
        return paginationDTO;
    }

    public Integer unreadCount(Long id) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",id);
        queryWrapper.eq("status",NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.selectCount(queryWrapper);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        Notification updateNotify = new Notification();
        updateNotify.setId(notification.getId());
        updateNotify.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(updateNotify);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
