package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.entity.OrderComment;
import com.secondhand.service.CommentService;
import com.secondhand.vo.OrderCommentBuyerInfoVO;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;  // ✅ Spring Boot 3.x 正确包
// import javax.annotation.Resource;  // ❌ 旧包，仅适用于 Spring Boot 2.x@RestController
//@RequestMapping("/product/brand")
//public class BrandController {
//    @Resource
//    private BrandMapper brandMapper;
//
//    @GetMapping("/list")
//    public Result list() {
//        List<Brand> list = brandMapper.selectList(null);
//        return Result.success(list);
//    }
//}
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody OrderComment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/product/{productId}")
    public CommonResult<List<OrderComment>> list(@PathVariable Long productId) {
        return commentService.getByProductId(productId);
    }
    @GetMapping("/get/{id}")
    public CommonResult<OrderCommentBuyerInfoVO> get(@PathVariable Long id) {
        return commentService.getAllMyComment(id);
    }
}