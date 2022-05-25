package com.bbubbush.board.dto.req;

import com.bbubbush.board.vo.board.BoardTagVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public class ReqInsertArticle {

  private String subject;
  private String text;
  private String writer;
  @Builder.Default
  private List<String> tags = new ArrayList<>();

  private Long targetArticleId;

  public List<BoardTagVO> converBoardTagVO() {
    return tags.stream().map(tag -> new BoardTagVO(targetArticleId, tag, writer))
      .collect(Collectors.toList());

  }

}
