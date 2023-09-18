package com.sideproject.makeboard.dto;

import lombok.Data;

@Data
public class ReplyUpdateInfo {
    Long replyId;
    Long postId;
    String content;
    String password;
}
