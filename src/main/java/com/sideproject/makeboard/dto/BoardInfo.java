package com.sideproject.makeboard.dto;

import lombok.Data;


import java.util.Date;
@Data
public class BoardInfo {
    private Long id;
    private String title;
    private String nickName;
    private Date updateDate;
}
