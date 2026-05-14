package com.secondhand.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.vice.News;
import com.secondhand.service.NewsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Resource
    private NewsService newsService;

    /**
     * 新增新闻，默认状态 status=1 展示
     */
    @PostMapping("/add")
    public CommonResult add(@RequestBody News news) {
        // 新增默认展示
        news.setStatus(1);
        newsService.save(news);
        return CommonResult.success("新闻添加成功");
    }

    /**
     * 根据ID删除单条新闻
     */
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        newsService.removeById(id);
        return CommonResult.success("删除成功");
    }

    /**
     * 获取所有新闻（后台管理全量查询，所有状态都查）
     */
    @GetMapping("/list")
    public CommonResult<List<News>> getAllNews() {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<News> list = newsService.list(wrapper);
        return CommonResult.success(list);
    }

    /**
     * 一键删除所有新闻
     */
    @DeleteMapping("/deleteAll")
    public CommonResult deleteAllNews() {
        newsService.remove(new QueryWrapper<>());
        return CommonResult.success("全部新闻已清空");
    }

    /**
     * 修改新闻状态 1展示 2隐藏
     */
    @PutMapping("/updateStatus/{id}/{status}")
    public CommonResult updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        News news = new News();
        news.setId(id);
        news.setStatus(status);
        newsService.updateById(news);
        return CommonResult.success("状态修改成功");
    }
}