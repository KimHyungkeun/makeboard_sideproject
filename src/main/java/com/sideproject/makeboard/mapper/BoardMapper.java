package com.sideproject.makeboard.mapper;

import com.sideproject.makeboard.dto.BoardInfo;
import com.sideproject.makeboard.dto.BoardInfoWithId;
import com.sideproject.makeboard.dto.ReplyInfoAll;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BoardMapper {

    Long getBoardTotalCount ();
    List<BoardInfo> getBoardInfo (Long offset, Long listCnt);
    BoardInfoWithId getBoardInfoWithId (Long id);

    List<ReplyInfoAll> getReplyInfoAll (Long postId);

    Long setBoardInfo(String nickName, String password, String title, String content);

    void putBoardInfo(Long id, String password, String title, String content);

    void deleteBoardInfo(Long id, String password);

    Long setBoardReplyInfo(Long postId, Long parentId, String content, String nickname, String password);

    boolean isExistsId(Long id);

    boolean isExistsReplyParentId(Long parentId);

    boolean isCorrectPw(Long id, String password);
}
