package com.sideproject.makeboard.dto;

import lombok.Data;

@Data
public class ReplyUpdateInfo {
    Long replyId;
    String content;
    String password;
}
