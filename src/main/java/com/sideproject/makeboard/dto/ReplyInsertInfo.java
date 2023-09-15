package com.sideproject.makeboard.dto;

import lombok.Data;

@Data
public class ReplyInsertInfo {
    Long postId;
    Long parentId;
    String content;
    String nickname;
    String password;
}
