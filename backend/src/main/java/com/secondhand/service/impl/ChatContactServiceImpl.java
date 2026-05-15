package com.secondhand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secondhand.common.CommonResult;
import com.secondhand.entity.ChatContact;
import com.secondhand.entity.UserInfo;
import com.secondhand.mapper.ChatContactMapper;
import com.secondhand.mapper.UserInfoMapper;
import com.secondhand.service.ChatContactService;
import com.secondhand.vo.ContactListVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatContactServiceImpl implements ChatContactService {
    @Resource
    ChatContactMapper chatContactMapper;
    @Resource
    UserInfoMapper userInfoMapper;
    @Override
    public CommonResult<?> addContact(Long userId, Long sellerId) {
        if(isContacted(userId,sellerId)!=null){
            Long contactId = isContacted(userId, sellerId).getId();
            System.out.println("contactId:"+contactId);
            return CommonResult.success(contactId);
        }
        ChatContact chatContact = new ChatContact();
        chatContact.setStatus(1);
        chatContact.setUserOne(Math.min(userId, sellerId));
        chatContact.setUserTwo(Math.max(userId, sellerId));
//        chatContact.set
        chatContactMapper.insert(chatContact);
        Long chatContactId = chatContact.getId();
        return CommonResult.success(chatContactId);
    }
    public ChatContact isContacted(Long userId, Long sellerId) {
        return chatContactMapper.selectOne(new LambdaQueryWrapper<ChatContact>()
                .eq(ChatContact::getUserOne,Math.min(userId,sellerId))
                .eq(ChatContact::getUserTwo,Math.max(userId,sellerId)));
    }

    public List<ContactListVO> getContactList(Long userId) {
        return chatContactMapper.selectList(new LambdaQueryWrapper<ChatContact>()
                // 条件：user_one 或 user_two 等于当前用户ID
                .eq(ChatContact::getStatus, 1) // 只查正常状态
                .and(w -> w
                        .eq(ChatContact::getUserOne, userId)
                        .or()
                        .eq(ChatContact::getUserTwo, userId)
                )
                .orderByDesc(ChatContact::getUpdateTime)
        ).stream().map(contact -> {
            ContactListVO vo = new ContactListVO();
            vo.setContactId(contact.getId().intValue());
            vo.setLastMsg(contact.getLastMsg());

            // 关键：判断哪个是自己，另一个就是聊天对象
            Long chatUserId;
            if (contact.getUserOne().equals(userId)) {
                chatUserId = contact.getUserTwo();
            } else {
                chatUserId = contact.getUserOne();
            }
            vo.setChatUserId(chatUserId.intValue());

            // 根据 chatUserId 查询用户信息
            UserInfo userInfo = userInfoMapper.selectById(chatUserId);
            if (userInfo != null) {
                vo.setChatUsername(userInfo.getUsername());
                vo.setAvatar(userInfo.getAvatar());
            }
            return vo;
        }).collect(Collectors.toList());
    }
}
