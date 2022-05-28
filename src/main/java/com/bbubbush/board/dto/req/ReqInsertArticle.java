package com.bbubbush.board.dto.req;

import com.bbubbush.board.annotation.DuplicateListValue;
import com.bbubbush.board.vo.board.BoardTagVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public class ReqInsertArticle {

  @NotBlank(message = "INVALID_PARAMETER")
  private String subject;
  @NotBlank(message = "INVALID_PARAMETER")
  @Size(max = 200, message = "TOO_LONG_TEXT")
  private String text;
  private String writer;
  @Builder.Default
  @Size(max = 5, message = "TOO_MANY_COLLECTION")
  @DuplicateListValue
  private List<String> tags = new ArrayList<>();
  private Long targetArticleId;

  public List<BoardTagVO> convertBoardTagVO() {
    return tags.stream().map(tag -> new BoardTagVO(targetArticleId, tag, writer))
      .collect(Collectors.toList());
  }

}
