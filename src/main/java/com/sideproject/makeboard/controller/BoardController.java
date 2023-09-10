package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.*;
import com.sideproject.makeboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "게시판 API 목록")
@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 목록 전체 불러오기


    @GetMapping("list")
    @Operation(summary = "게시판 전체목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 내용 모두 불러오기"),
    })
    public @ResponseBody List<BoardInfo> getBoardInfo(
            @RequestParam(value = "page", required = false)Long page,
            @RequestParam(value = "listCnt", required = false) Long listCnt) {
        if (page == null) { page = (long)1; }
        if (listCnt == null) { listCnt = (long)10; }

        return boardService.getBoardInfo(page, listCnt);
    }

    // 특정 게시판 불러오기
    @GetMapping("view")
    @Operation(summary = "특정 게시글 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "특정 게시글 불러오기"),
            @ApiResponse(responseCode = "404", description = "게시글 없음"),
    })
    public ResponseEntity<BoardInfoWithId> getBoardInfoWithId(@RequestParam(value = "id")Long id) {

        BoardInfoWithId boardInfoWithId = boardService.getBoardInfoWithId(id);
        if (!boardService.isExistsId(id)) {
            return new ResponseEntity<BoardInfoWithId>(boardInfoWithId , HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<BoardInfoWithId>(boardInfoWithId , HttpStatus.OK);

    }

    // 게시판에 글 생성
    @PostMapping
    @Operation(summary = "게시판에 글을 새로 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "requestBody 중 필요한 영역에 대해 기입하지 않음"),
    })
    public ResponseEntity<?> setBoardInfo(@RequestBody BoardInsertInfo boardInsertInfo) {

        if (boardInsertInfo.getNickname() == null || boardInsertInfo.getNickname().isEmpty()) {
            return new ResponseEntity<String>("Nickname is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (boardInsertInfo.getPassword() == null || boardInsertInfo.getPassword().isEmpty()) {
            return new ResponseEntity<String>("Password is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (boardInsertInfo.getTitle() == null || boardInsertInfo.getTitle().isEmpty()) {
            return new ResponseEntity<String>("Title is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (boardInsertInfo.getTitle().length() > 100) {
            return new ResponseEntity<String>("Title Length over 100", HttpStatus.BAD_REQUEST);
        }

        if (boardInsertInfo.getNickname().length() > 30) {
            return new ResponseEntity<String>("Nickname Length over 30", HttpStatus.BAD_REQUEST);
        }

        if (boardInsertInfo.getContent().length() > 1000) {
            return new ResponseEntity<String>("Content Length over 1000", HttpStatus.BAD_REQUEST);
        }

        Long id = boardService.setBoardInfo(boardInsertInfo);
        Map<String, Long> result = new HashMap<>();
        result.put("id", id);
        return new ResponseEntity<Map<String, Long>>(result, HttpStatus.CREATED);
    }

    // 게시판 내의 글을 수정
    @PutMapping
    @Operation(summary = "게시글 내용을 수정하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "requestBody에서 필요한 영역에 대해 기입하지 않음"),
            @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음"),
    })
    public ResponseEntity<String> putBoardInfo(@RequestBody BoardUpdateInfo boardUpdateInfo) {

        if (boardUpdateInfo.getId() == null) {
            return new ResponseEntity<String>("Id is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (boardUpdateInfo.getPassword() == null || boardUpdateInfo.getPassword().isEmpty()) {
            return new ResponseEntity<String>("Password is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (boardUpdateInfo.getTitle() == null || boardUpdateInfo.getTitle().isEmpty()) {
            return new ResponseEntity<String>("Title is required" , HttpStatus.BAD_REQUEST); //400 Code (ERROR)
        }

        if (!boardService.isExistsId(boardUpdateInfo.getId())) {
            return new ResponseEntity<String>("Post Id : " +  boardUpdateInfo.getId().toString() + " does not exists", HttpStatus.NOT_FOUND); //404 Code (ERROR)
        }

        if (!boardService.isCorrectPw(boardUpdateInfo.getId(), boardUpdateInfo.getPassword())) {
            return new ResponseEntity<String>("Password not correct", HttpStatus.BAD_REQUEST);
        }

        if (boardUpdateInfo.getTitle().length() > 100) {
            return new ResponseEntity<String>("Title Length over 100", HttpStatus.BAD_REQUEST);
        }

        boardService.putBoardInfo(boardUpdateInfo);
        return new ResponseEntity<String>("Post is updated", HttpStatus.OK);
    }

    // 게시판 내의 글을 삭제
    @DeleteMapping
    @Operation(summary = "게시글 삭제하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "삭제에 있어 필요한 id나 password가 미입력되거나 잘못 기입됨"),
            @ApiResponse(responseCode = "404", description = "삭제하려는 글이 존재하지 않음"),
    })
    public ResponseEntity<String> deleteBoardInfo(@RequestParam(value = "id") Long id,
                                                  @RequestParam(value = "password")String password) {

        if (!boardService.isExistsId(id)) {
            return new ResponseEntity<String>("Post id : " + id.toString() + " does not exists" , HttpStatus.NOT_FOUND); // 404 Code (ERROR)
        }

        if (!boardService.isCorrectPw(id, password)) {
            return new ResponseEntity<String>("Password not correct", HttpStatus.BAD_REQUEST); // 400 Code (ERROR)
        }

        boardService.deleteBoardInfo(id, password);
        return new ResponseEntity<String>("Post id : " + id.toString() + " is deleted",HttpStatus.OK);
    }

}
