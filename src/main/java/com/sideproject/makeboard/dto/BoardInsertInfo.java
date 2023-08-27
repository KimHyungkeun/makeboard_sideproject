package com.sideproject.makeboard.dto;

import lombok.Data;

@Data
public class BoardInsertInfo {
    private String nickname;
    private String password;
    private String title;
    private String content;
}
