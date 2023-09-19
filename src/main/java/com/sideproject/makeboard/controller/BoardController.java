package com.sideproject.makeboard.controller;

import com.sideproject.makeboard.dto.*;
import com.sideproject.makeboard.service.BoardService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OpenAPIDefinition(servers = {@Server(url = "${WEB_SERVER_URL}", description = "Default Server URL")})
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
    public @ResponseBody ResponseEntity<BoardInfoAll> getBoardInfo(
            @RequestParam(value = "page", required = false)Long page,
            @RequestParam(value = "listCnt", required = false) Long listCnt) {
        if (page == null) { page = (long)1; }
        if (listCnt == null) { listCnt = (long)10; }

        BoardInfoAll boardInfoAll = new BoardInfoAll();
        boardInfoAll.setTotalBoardCount(boardService.getBoardTotalCount());
        boardInfoAll.setBoardInfo(boardService.getBoardInfo(page, listCnt));

        return new ResponseEntity<BoardInfoAll>(boardInfoAll, HttpStatus.OK);
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


    @GetMapping("replyCount")
    @Operation(summary = "게시글 별 댓글 개수 표시")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "게시글 전체 갯수 불러오기 성공")})
    public ResponseEntity<?> getBoardReplyCount () {
        return null;
    }

    @GetMapping("reply")
    @Operation(summary = "해당 게시글에 대한 댓글 전체 조회하기")
    @ApiResponses(value =
            {@ApiResponse(responseCode = "200", description = "게시글 전체 갯수 불러오기 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 ID 미존재")})
    public ResponseEntity<?> getBoardReplyWithPostId (@RequestParam(value = "postId") Long postId) {

        List<ReplyInfoAll> replyInfoAlls = boardService.getReplyInfoAll(postId);
        if (!boardService.isExistsId(postId)) {
            return new ResponseEntity<String>("PostId : " + postId.toString() + " does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ReplyInfoAll>>(replyInfoAlls, HttpStatus.OK);
    }

    @PostMapping("reply")
    @Operation(summary = "댓글 생성 (+대댓글 생성)")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "댓글 생성 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 ID 미입력, 닉네임 미입력, 패스워드 미입력"),
            @ApiResponse(responseCode = "404", description = "게시글 ID나 부모댓글 ID가 없음")})
    public ResponseEntity<?> setBoardReply (@RequestBody ReplyInsertInfo replyInsertInfo) {
        if (replyInsertInfo.getPostId() == null) {
            return new ResponseEntity<String>("PostId is required", HttpStatus.BAD_REQUEST);
        }

        if (replyInsertInfo.getNickname() == null || replyInsertInfo.getNickname().isEmpty()) {
            return new ResponseEntity<String>("Nickname is required", HttpStatus.BAD_REQUEST);
        }

        if (replyInsertInfo.getPassword() == null || replyInsertInfo.getPassword().isEmpty()) {
            return new ResponseEntity<String>("Password is required", HttpStatus.BAD_REQUEST);
        }

        if (!boardService.isExistsId(replyInsertInfo.getPostId())) {
            return new ResponseEntity<String>("PostId : " + replyInsertInfo.getPostId().toString() + " does not exist", HttpStatus.NOT_FOUND);
        }

        if (replyInsertInfo.getParentId() != null && !boardService.isExistsReplyId(replyInsertInfo.getParentId())) {
            return new ResponseEntity<String>("Reply ParentId : " + replyInsertInfo.getParentId().toString() + " does not exist", HttpStatus.NOT_FOUND);
        }

        Long replyId = boardService.setBoardReply(replyInsertInfo);
        Map<String, Long> result = new HashMap<>();
        result.put("postId", replyInsertInfo.getPostId());
        result.put("replyId", replyId);
        if (replyInsertInfo.getParentId() == null) {
            result.put("parentId", replyId);
        } else {
            result.put("parentId", replyInsertInfo.getParentId());
        }
        return new ResponseEntity<Map<String, Long>>(result, HttpStatus.CREATED);
    }

    @PutMapping("reply")
    @Operation(summary = "댓글 내용 수정")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "댓글 수정 완료"),
            @ApiResponse(responseCode = "400", description = "댓글 ID나 암호를 미입력. 암호를 틀렸을 때도 발생"),
            @ApiResponse(responseCode = "404", description = "게시글 ID가 미존재")})
    public ResponseEntity<String> putBoardReply (@RequestBody ReplyUpdateInfo replyUpdateInfo) {

        if (replyUpdateInfo.getReplyId() == null) {
            return new ResponseEntity<String>("ReplyId is required", HttpStatus.BAD_REQUEST);
        }

        if (replyUpdateInfo.getPassword() == null || replyUpdateInfo.getPassword().isEmpty()) {
            return new ResponseEntity<String>("Password is required", HttpStatus.BAD_REQUEST);
        }

        if (!boardService.isCorrectReplyPw(replyUpdateInfo.getReplyId(), replyUpdateInfo.getPassword())) {
            return new ResponseEntity<String>("Password not correct", HttpStatus.BAD_REQUEST);
        }

        if (replyUpdateInfo.getPostId() == null || !boardService.isExistsId(replyUpdateInfo.getPostId())) {
            return new ResponseEntity<String>("PostId does not exist", HttpStatus.NOT_FOUND);
        }

        boardService.putBoardReplyInfo(replyUpdateInfo);
        return new ResponseEntity<String>("Reply is updated", HttpStatus.OK);
    }

    @DeleteMapping("reply")
    @Operation(summary = "댓글 삭제")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "댓글 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "댓글 ID, 게시글 ID, 암호를 미입력. 암호를 틀렸을때도 발생"),
            @ApiResponse(responseCode = "404", description = "댓글 ID가 미존재 또는 댓글 ID가 미존재")})
    public ResponseEntity<String> deleteBoardReply (@RequestParam(value = "replyId")Long replyId,
                                                    @RequestParam(value = "postId")Long postId,
                                                    @RequestParam(value = "parentId")Long parentId,
                                               @RequestParam(value="password")String password) {
        if (!boardService.isExistsReplyId(replyId)) {
            return new ResponseEntity<String>("ReplyId: " + replyId.toString() + " does not exist", HttpStatus.NOT_FOUND);
        }

        if (!boardService.isExistsId(postId)) {
            return new ResponseEntity<String>("PostId: " + postId.toString() + " does not exist", HttpStatus.NOT_FOUND);
        }

        if (!boardService.isCorrectReplyPw(replyId, password)) {
            return new ResponseEntity<String>("Password not correct", HttpStatus.BAD_REQUEST);
        }

        Boolean isParent = boardService.isParentReply(replyId);
        boardService.deleteBoardReplyInfo(replyId, postId, parentId, password, isParent);
        return new ResponseEntity<String>("Reply is deleted", HttpStatus.OK);
    }

}
