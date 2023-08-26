package com.sideproject.makeboard.service;


import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<BoardInfo> boardInfos = boardMapper.getBoardInfo(offset, listCnt);
        return boardInfos;
    }
}
