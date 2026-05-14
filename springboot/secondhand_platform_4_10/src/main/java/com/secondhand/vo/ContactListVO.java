package com.secondhand.vo;

import lombok.Data;

@Data
public class ContactListVO {
    private int contactId;
    private int chatUserId;
    private String chatUsername;
    private String lastMsg;
    private String avatar;
}
