package com.sideproject.makeboard.mapper;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BoardMapper {
    List<BoardInfo> getBoardInfo (Integer offset, Integer listCnt);
    BoardInfoWithId getBoardInfoWithId (Integer id);

    void setBoardInfo();
}
