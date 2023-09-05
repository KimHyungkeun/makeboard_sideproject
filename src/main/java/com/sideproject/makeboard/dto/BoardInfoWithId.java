package com.sideproject.makeboard.dto;

import lombok.Data;


import java.util.Date;
@Data
public class BoardInfoWithId {
    private String nickname;
    private String title;
    private String content;
    private Date updateDate;
}
