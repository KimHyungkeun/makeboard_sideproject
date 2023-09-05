package com.sideproject.makeboard.service;


import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import com.sideproject.makeboard.dto.BoardInsertInfo;
import com.sideproject.makeboard.dto.BoardUpdateInfo;
import com.sideproject.makeboard.mapper.BoardMapper;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;
    private BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<BoardInfo> getBoardInfo (Long page, Long listCnt) {

        long offset = (page * listCnt) - listCnt;
        return boardMapper.getBoardInfo(offset, listCnt);

    }

    public BoardInfoWithId getBoardInfoWithId (Long id) {
        BoardInfoWithId boardInfo = boardMapper.getBoardInfoWithId(id);
        return boardInfo;
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

    public void deleteBoardInfo (Long id, String password) {
        boardMapper.deleteBoardInfo(id, password);
    }

    public boolean isExistsId (Long id) {
        return boardMapper.isExistsId(id);
    }

    public boolean isCorrectPw (Long id, String password) {
        return boardMapper.isCorrectPw(id, password);
    }


}
