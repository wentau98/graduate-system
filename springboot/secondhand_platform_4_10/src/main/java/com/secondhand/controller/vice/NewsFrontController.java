package com.secondhand.controller.vice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.vice.News;
import com.secondhand.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/news")
public class NewsFrontController {

    @Resource
    private NewsService newsService;

    /**
     * 前台用户获取展示的新闻（只查 status=1）
     */
    @GetMapping("/list")
    public CommonResult<List<News>> getShowNews() {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.orderByDesc("create_time");
        List<News> list = newsService.list(wrapper);
        return CommonResult.success(list);
    }
}