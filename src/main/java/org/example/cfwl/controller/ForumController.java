package org.example.cfwl.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.cfwl.common.BaseResponse;
import org.example.cfwl.config.PreAuthorize;
import org.example.cfwl.context.BaseContext;
import org.example.cfwl.model.forum.dto.ForumPostDto;
import org.example.cfwl.model.forum.dto.ForumPostPage;
import org.example.cfwl.model.forum.dto.ForumPostSearchDto;
import org.example.cfwl.model.forum.po.ForumPost;
import org.example.cfwl.model.forum.vo.ForumPostInfoVo;
import org.example.cfwl.model.forum.vo.ForumPostSummaryInfoVo;
import org.example.cfwl.model.login.dto.LoginDto;
import org.example.cfwl.model.user.po.User;
import org.example.cfwl.model.user.vo.UserVo;
import org.example.cfwl.service.ForumPostService;
import org.example.cfwl.service.LoginService;
import org.example.cfwl.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Resource
    private LoginService loginService;
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

    /**
     * 获取帖子概要信息
     *
     * @return List<ForumPostSummaryInfoVo>
     */
    @GetMapping("/getForumSummaryInfo")
    private BaseResponse<IPage<ForumPostSummaryInfoVo>> getForumSummaryInfo(@RequestBody ForumPostPage forumPostPage) {
        IPage<ForumPost> forumPostIPage = forumPostService.getForumSummaryInfo(forumPostPage);
        List<ForumPostSummaryInfoVo> forumPostSummaryInfoVos = BeanUtil.copyToList(forumPostIPage.getRecords(), ForumPostSummaryInfoVo.class);
        List<User> users = loginService.getUsersById(forumPostSummaryInfoVos);
        // 创建用户ID到用户的映射
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        // 赋值用户名
        for (ForumPostSummaryInfoVo vo : forumPostSummaryInfoVos) {
            User user = userMap.get(vo.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
        }
        IPage<ForumPostSummaryInfoVo> forumPostSummaryInfoVoIPage = new Page<>();
        forumPostSummaryInfoVoIPage.setRecords(forumPostSummaryInfoVos);
        forumPostSummaryInfoVoIPage.setTotal(forumPostIPage.getTotal());
        forumPostSummaryInfoVoIPage.setSize(forumPostIPage.getSize());
        forumPostSummaryInfoVoIPage.setCurrent(forumPostIPage.getCurrent());
        return ResultUtil.success(forumPostSummaryInfoVoIPage);
    }

    /**
     *搜索帖子
     * @return List<ForumPostSummaryInfoVo>
     */
    @PostMapping("/searchForumPost")
    private BaseResponse<IPage<ForumPostSummaryInfoVo>> searchForumPost(@RequestBody ForumPostSearchDto forumPostSearchDto) throws IOException {
        org.springframework.data.domain.Page<ForumPost> forumPostIPage = forumPostService.getForumSummaryInfoByKey(forumPostSearchDto);
        List<ForumPostSummaryInfoVo> forumPostSummaryInfoVos = BeanUtil.copyToList(forumPostIPage.getContent(), ForumPostSummaryInfoVo.class);
        List<User> users = loginService.getUsersById(forumPostSummaryInfoVos);
        // 创建用户ID到用户的映射
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        // 赋值用户名
        for (ForumPostSummaryInfoVo vo : forumPostSummaryInfoVos) {
            User user = userMap.get(vo.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
            }
        }
        IPage<ForumPostSummaryInfoVo> forumPostSummaryInfoVoIPage = new Page<>();
        forumPostSummaryInfoVoIPage.setRecords(forumPostSummaryInfoVos);
        forumPostSummaryInfoVoIPage.setTotal(forumPostIPage.getTotalElements());
        forumPostSummaryInfoVoIPage.setSize(forumPostIPage.getSize());
        return ResultUtil.success(forumPostSummaryInfoVoIPage);
    }
}
