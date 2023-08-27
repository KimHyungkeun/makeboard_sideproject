package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInsertInfo;
import com.sideproject.makeboard.service.BoardService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 20230827 406 코드 발생
    @GetMapping("view")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody BoardInfo getBoardInfoWithId(@RequestParam(value = "id")Integer id) {
        return boardService.getBoardInfoWithId(id);
    }
    
    // 20230827 @RequestBody가 먹질 않는것인지, get이나 set할때 지정한 value가 안나옴 
    @PostMapping
    public ResponseEntity<BoardInsertInfo> setBoardInfo(@RequestBody BoardInsertInfo boardInsertInfo) {
        return null;
    }
}
