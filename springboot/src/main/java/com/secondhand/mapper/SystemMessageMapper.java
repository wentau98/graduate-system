package com.secondhand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.secondhand.entity.ChatMessage;
import com.secondhand.entity.SystemMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface SystemMessageMapper extends BaseMapper<ChatMessage> {
    @Select("SELECT contact_id FROM chat_message WHERE from_user_id = #{s} and to_user_id = #{toUserId}")
    Long getContactId(String toUserId, String s);
//    @Insert("INSERT INTO system_message(user_id,msg_title,msg_content,msg_type) VALUES(#{userId},#{msgTitle},#{msgContent},#{msgType})")
//    int insert(ChatMessage msg);
//
//    @Select("SELECT * FROM system_message WHERE user_id=#{uid}")
//    List<SystemMessage> selectByUserId(Long uid);
}