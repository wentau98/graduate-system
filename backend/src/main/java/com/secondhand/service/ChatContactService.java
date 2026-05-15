package com.secondhand.service;

import com.secondhand.common.CommonResult;
import com.secondhand.vo.ContactListVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ChatContactService {
    CommonResult<?> addContact(Long userId, Long sellerId);

    List<ContactListVO> getContactList(Long userId);
}
