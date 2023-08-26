package com.sideproject.makeboard.mapper;

import com.sideproject.makeboard.dto.BoardInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BoardMapper {
    List<BoardInfo> getBoardInfo (Integer offset, Integer listCnt);
}
