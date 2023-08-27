package com.sideproject.makeboard.service;


import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInsertInfo;
import com.sideproject.makeboard.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<BoardInfo> getBoardInfo (Integer page, Integer listCnt) {

        int offset = (page * listCnt) - listCnt;
        return boardMapper.getBoardInfo(offset, listCnt);

    }

    public BoardInfo getBoardInfoWithId (Integer id) {
        BoardInfo boardInfo = boardMapper.getBoardInfoWithId(id);
        return boardInfo;
    }

    public void setBoardInfo (BoardInsertInfo boardInsertInfo) {

    }


}
