package com.secondhand.controller.vice;

import com.secondhand.common.CommonResult;
import com.secondhand.common.FileLocation;
import com.secondhand.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @PostMapping("/upload/product")
    public Object uploadProductImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = FileUtil.upload(file, FileLocation.PRODUCT_LOCATION,FileLocation.PRODUCT);
        return CommonResult.success(url);
    }
    @PostMapping("/upload/user")
    public Object uploadUserImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = FileUtil.upload(file, FileLocation.USER_LOCATION,FileLocation.USER);
        return CommonResult.success(url);
    }
    @PostMapping("/upload/brand")
    public Object uploadBrandImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = FileUtil.upload(file, FileLocation.BRAND_LOCATION,FileLocation.BRAND);
        return CommonResult.success(url);
    }
}