package com.sideproject.makeboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.util.Date;
@Data
@JsonInclude
public class BoardInfoWithId {
    private String nickName;
    private String title;
    private String content;
    private Date updateDate;
}
