package com.sideproject.makeboard.service;


import com.sideproject.makeboard.dto.*;
import com.sideproject.makeboard.mapper.BoardMapper;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public Long getBoardTotalCount () {
        return boardMapper.getBoardTotalCount();
    }

    public List<BoardInfo> getBoardInfo (Long page, Long listCnt) {

        long offset = (page * listCnt) - listCnt;
        return boardMapper.getBoardInfo(offset, listCnt);

    }

    public BoardInfoWithId getBoardInfoWithId (Long id) {
        BoardInfoWithId boardInfo = boardMapper.getBoardInfoWithId(id);
        return boardInfo;
    }

    public List<ReplyInfoAll> getReplyInfoAll (Long postId) {
        ReplyInfoAll replyInfoAll = new ReplyInfoAll();
        return boardMapper.getReplyInfoAll(postId);
    }

    public Long setBoardInfo (BoardInsertInfo boardInsertInfo) {
        String nickName = boardInsertInfo.getNickname();
        String password = boardInsertInfo.getPassword();
        String title = boardInsertInfo.getTitle();
        String content = boardInsertInfo.getContent();
        return boardMapper.setBoardInfo(nickName, password, title, content);
    }

    public void putBoardInfo (BoardUpdateInfo boardUpdateInfo) {
        Long id = boardUpdateInfo.getId();
        String password = boardUpdateInfo.getPassword();
        String title = boardUpdateInfo.getTitle();
        String content = boardUpdateInfo.getContent();
        boardMapper.putBoardInfo(id, password, title, content);
    }

    public void putBoardReplyInfo (ReplyUpdateInfo replyUpdateInfo) {
        Long replyId = replyUpdateInfo.getReplyId();
        String content = replyUpdateInfo.getContent();
        String password = replyUpdateInfo.getPassword();
        boardMapper.putBoardReplyInfo(replyId, content, password);
    }

    public void deleteBoardInfo (Long id, String password) {
        boardMapper.deleteBoardInfo(id, password);
    }

    public void deleteBoardReplyInfo (Long replyId, Long postId, String password, Boolean isParent) {
        boardMapper.deleteBoardReplyInfo(replyId, postId, password, isParent);
    }

    public Long setBoardReply (ReplyInsertInfo replyInsertInfo) {
        Long postId = replyInsertInfo.getPostId();
        Long parentId;
        if (replyInsertInfo.getParentId() == null) {
            parentId = null;
        } else {
            parentId = replyInsertInfo.getParentId();
        }
        String content = replyInsertInfo.getContent();
        String nickname = replyInsertInfo.getNickname();
        String password = replyInsertInfo.getPassword();

        return boardMapper.setBoardReplyInfo(postId, parentId, content, nickname, password);
    }

    public boolean isExistsId (Long id) {
        return boardMapper.isExistsId(id);
    }

    public boolean isExistsReplyId (Long parentId) {return boardMapper.isExistsReplyId(parentId);}

    public boolean isCorrectPw (Long id, String password) {
        return boardMapper.isCorrectPw(id, password);
    }

    public boolean isCorrectReplyPw (Long replyId, String password) {
        return boardMapper.isCorrectReplyPw(replyId, password);
    }

    public boolean isParentReply (Long replyId) {
        return boardMapper.isParentReply(replyId);
    }
}
