package com.bbubbush.board.dto.req;

import com.bbubbush.board.annotation.DuplicateListValue;
import com.bbubbush.board.vo.board.BoardTagVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @ApiModelProperty(value = "제목", example = "Hello world!")
  private String subject;
  @NotBlank(message = "INVALID_PARAMETER")
  @Size(max = 200, message = "TOO_LONG_TEXT")
  @ApiModelProperty(value = "내용", example = "Learning mate")
  private String text;
  @ApiModelProperty(value = "작성자", example = "bbubbush")
  private String writer;
  @Builder.Default
  @Size(max = 5, message = "TOO_MANY_COLLECTION")
  @DuplicateListValue
  @ApiModelProperty(value = "해시태그", example = "[\"Spring\", \"Java\"]")
  private List<String> tags = new ArrayList<>();
  @ApiModelProperty(hidden = true)
  private Long targetArticleId;

  public List<BoardTagVO> convertBoardTagVO() {
    return tags.stream().map(tag -> new BoardTagVO(targetArticleId, tag, writer))
      .collect(Collectors.toList());
  }

}
