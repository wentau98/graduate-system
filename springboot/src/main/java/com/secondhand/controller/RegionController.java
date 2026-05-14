package com.secondhand.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.Region;
import com.secondhand.mapper.RegionMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Resource
    private RegionMapper regionMapper;

    @GetMapping("/list")
    public CommonResult<List<Region>> list(
            @RequestParam(required = false) Integer level,
            @RequestParam(required = false) Integer parentId
    ) {
        LambdaQueryWrapper<Region> wrapper = new LambdaQueryWrapper<>();
        if (level != null) {
            wrapper.eq(Region::getLevel, level);
        }
        if (parentId != null) {
            wrapper.eq(Region::getParentId, parentId);
        }
        wrapper.orderByAsc(Region::getSort);
        return CommonResult.success(regionMapper.selectList(wrapper));
    }
    @GetMapping("/get")
    public CommonResult<List<?>> get(@RequestParam String provinceName, @RequestParam String cityName) {
        Integer provinceId = null;
        Integer cityId = null;
        if (provinceName != null && cityName != null) {
            LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Region::getRegionName, provinceName);
            queryWrapper.eq(Region::getLevel, 1);
            Region province = regionMapper.selectOne(queryWrapper);
            provinceId = province.getRegionId();
            LambdaQueryWrapper<Region> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(Region::getRegionName, cityName);
            queryWrapper2.eq(Region::getLevel, 2);
            Region region2 = regionMapper.selectOne(queryWrapper2);
            cityId = region2.getRegionId();
            return CommonResult.success(List.of(provinceId,cityId));
        }
        return CommonResult.success(List.of());
    }
}