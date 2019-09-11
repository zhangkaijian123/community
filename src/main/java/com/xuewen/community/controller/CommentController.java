package com.xuewen.community.controller;

import com.xuewen.community.dto.CommentCreateDTO;
import com.xuewen.community.dto.CommentDTO;
import com.xuewen.community.dto.DianZanTDO;
import com.xuewen.community.dto.ResultDTO;
import com.xuewen.community.enums.CommentTypeEnum;
import com.xuewen.community.exception.CustomizeErrorCode;
import com.xuewen.community.exception.CustomizeException;
import com.xuewen.community.model.Comment;
import com.xuewen.community.model.User;
import com.xuewen.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 张铠建
 * @description
 * @createdate 2019-09-10 23:51
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        if (StringUtils.isBlank(commentCreateDTO.getContent()) || commentCreateDTO == null){
            throw new CustomizeException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

    @ResponseBody
    @RequestMapping(value = "/dianzan", method = RequestMethod.POST)
    public ResultDTO dianzan(@RequestBody DianZanTDO dianZanTDO){
        commentService.dianzan(dianZanTDO);
        return ResultDTO.okOf();
    }
}
