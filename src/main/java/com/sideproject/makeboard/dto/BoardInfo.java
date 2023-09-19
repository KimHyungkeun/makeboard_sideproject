package com.sideproject.makeboard.dto;

import lombok.Data;


import java.util.Date;
@Data
public class BoardInfo {
    private Long id;
    private String title;
    private String nickname;
    private Date updateDate;
    private Long replyCount;
}
