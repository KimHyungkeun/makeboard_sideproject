package com.sideproject.makeboard.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyInfoAll {
    private Long postId;
    private Long replyId;
    private Long parentId;
    private String content;
    private String nickname;
    private Date updateDate;
}
