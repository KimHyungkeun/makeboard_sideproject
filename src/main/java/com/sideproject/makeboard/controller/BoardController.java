package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping("list")
    public List<BoardInfo> getBoardInfo(Integer page, Integer listCnt) {
        if (page == null) { page = 1;}
        if (listCnt == null) { listCnt = 10;}

        return boardService.getBoardInfo(page, listCnt);
    }
}
