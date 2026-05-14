package com.secondhand.mapper;

import com.secondhand.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ChatMessageMapper {
    @Insert("INSERT INTO chat_message(sender_id,receiver_id,product_id,content) " +
            "VALUES(#{senderId},#{receiverId},#{productId},#{content})")
    int insert(ChatMessage msg);

    @Select("SELECT * FROM chat_message WHERE (sender_id=#{sId} AND receiver_id=#{rId}) OR (sender_id=#{rId} AND receiver_id=#{sId}) ORDER BY send_time ASC")
    List<ChatMessage> selectHistory(@Param("sId") Long sId, @Param("rId") Long rId);
}