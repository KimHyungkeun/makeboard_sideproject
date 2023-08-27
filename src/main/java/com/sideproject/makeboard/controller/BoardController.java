package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import com.sideproject.makeboard.dto.BoardInsertInfo;
import com.sideproject.makeboard.service.BoardService;


import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping("list")
    public @ResponseBody List<BoardInfo> getBoardInfo(@RequestParam(value = "page", required = false)Integer page, @RequestParam(value = "listCnt", required = false) Integer listCnt) {
        if (page == null) { page = 1;}
        if (listCnt == null) { listCnt = 10;}

        return boardService.getBoardInfo(page, listCnt);
    }

    @GetMapping("view")
    public @ResponseBody BoardInfoWithId getBoardInfoWithId(@RequestParam(value = "id")Integer id) {
        BoardInfoWithId boardInfoWithId = boardService.getBoardInfoWithId(id);
        return boardInfoWithId;
    }
    
    // 20230827 @RequestBody가 먹질 않는것인지, get이나 set할때 지정한 value가 안나옴
    @PostMapping
    public void setBoardInfo(@RequestBody BoardInsertInfo boardInsertInfo) {

    }
}
