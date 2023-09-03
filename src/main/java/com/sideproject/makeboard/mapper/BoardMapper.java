package com.sideproject.makeboard.mapper;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BoardMapper {
    List<BoardInfo> getBoardInfo (Long offset, Long listCnt);
    BoardInfoWithId getBoardInfoWithId (Long id);

    Long getRecentBoardId ();

    void setBoardInfo(String nickName, String password, String title, String content);

    void putBoardInfo(Long id, String password, String title, String content);

    void deleteBoardInfo(Long id, String password);

    boolean isExistsId(Long id);

    boolean isCorrectPw(Long id, String password);
}
