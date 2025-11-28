package org.example.cfwl.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.config.PreAuthorize;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.model.forum.dto.ForumPostDto;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.model.forum.vo.ForumPostInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.model.user.vo.UserVo;
import org.example.cfwl.service.ForumPostService;
import org.example.cfwl.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 张文化
 * @Description: 论坛控制类$
 * @DateTime: 2025/11/25$ 17:19$
 * @Params: $
 * @Return $
 */
@RestController
@RequestMapping("/forum")
@Slf4j
public class ForumController {
    @Resource
    private ForumPostService forumPostService;
    /**
     *新增帖子
     * @param forumPostDto 帖子信息
     */
    @PreAuthorize("forum:add")
    @PostMapping("/add")
    private BaseResponse<String> add(@RequestBody ForumPostDto forumPostDto) {
        ForumPost forumPost = BeanUtil.copyProperties(forumPostDto, ForumPost.class);
        forumPost.setUserId(BaseContext.getCurrentId());
        forumPost.setContentText(HtmlUtil.cleanHtmlTag(forumPost.getContent()));
        forumPostService.save(forumPost);
        return ResultUtil.success("提交成功");
    }

    /**
     * 通过id获取帖子详细信息
     *
     * @return ForumPostInfoVo
     */
    @PreAuthorize("forum:getForumInfoById")
    @GetMapping("/getForumInfoById")
    private BaseResponse<ForumPostInfoVo> getForumById(@RequestParam("id") Long id) {
        ForumPost forumPost = forumPostService.getById(id);
        ForumPostInfoVo forumPostInfoVo = BeanUtil.copyProperties(forumPost, ForumPostInfoVo.class);
        forumPostInfoVo.setUsername(BaseContext.getCurrentName());
        return ResultUtil.success(forumPostInfoVo);
    }
}
