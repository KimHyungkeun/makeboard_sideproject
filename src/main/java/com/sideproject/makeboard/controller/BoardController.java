package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import com.sideproject.makeboard.dto.BoardInsertInfo;
import com.sideproject.makeboard.dto.BoardUpdateInfo;
import com.sideproject.makeboard.service.BoardService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private BoardController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping("list")
    public @ResponseBody List<BoardInfo> getBoardInfo(
            @RequestParam(value = "page", required = false)Long page,
            @RequestParam(value = "listCnt", required = false) Long listCnt) {
        if (page == null) { page = (long)1; }
        if (listCnt == null) { listCnt = (long)10; }

        return boardService.getBoardInfo(page, listCnt);
    }

    @GetMapping("view")
    public @ResponseBody BoardInfoWithId getBoardInfoWithId(@RequestParam(value = "id")Long id) {
        BoardInfoWithId boardInfoWithId = boardService.getBoardInfoWithId(id);
        return boardInfoWithId;
    }

    @PostMapping
    public void setBoardInfo(@RequestBody BoardInsertInfo boardInsertInfo) {
        boardService.setBoardInfo(boardInsertInfo);
    }

    @PutMapping
    public void putBoardInfo(@RequestBody BoardUpdateInfo boardUpdateInfo) {
        boardService.putBoardInfo(boardUpdateInfo);
    }

    @DeleteMapping
    public void deleteBoardInfo(@RequestParam(value = "id") long id, @RequestParam(value = "password")String password) {
        boardService.deleteBoardInfo(id, password);
    }
}
