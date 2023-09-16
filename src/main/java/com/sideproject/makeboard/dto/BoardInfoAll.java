package com.sideproject.makeboard.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoardInfoAll {
    private Long totalBoardCount;
    private List<BoardInfo> boardInfo;
}
