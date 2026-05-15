package com.secondhand.controller.vice;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.vice.ActivityBanner;
import com.secondhand.mapper.vice.ActivityBannerMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class ActivityBannerController {

    @Resource
    private ActivityBannerMapper activityBannerMapper;

    // 获取当前生效的活动轮播图
    @GetMapping("/list")
    public CommonResult<List<ActivityBanner>> list() {
        List<ActivityBanner> banners = activityBannerMapper.selectActiveBanners();
        System.out.println("轮播图"+banners.toString());
        return CommonResult.success(banners);
    }
}