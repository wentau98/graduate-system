package com.secondhand.controller;

import com.secondhand.common.CommonResult;
import com.secondhand.service.ChatContactService;
import com.secondhand.vo.AddContactVO;
import com.secondhand.vo.ContactListVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    @Resource
    ChatContactService contactService;
    @PostMapping("/add")
    public CommonResult<?> addContact(@RequestBody AddContactVO addContactVO) {
        return contactService.addContact(addContactVO.getUserId(),addContactVO.getSellerId());
    }
    @GetMapping("/get/{userId}")
    public CommonResult<List<ContactListVO>> getContactList(@PathVariable String userId) {
        return CommonResult.success(contactService.getContactList(Long.parseLong(userId)));
    }
}
