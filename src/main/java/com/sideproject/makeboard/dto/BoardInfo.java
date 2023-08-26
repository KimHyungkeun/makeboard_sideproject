package com.sideproject.makeboard.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor  // 생성자를 사용하지 않도록 선언
public class BoardInfo {
    private Long id;
    private String title;
    private String nickName;
    private Date updateDate;
}
