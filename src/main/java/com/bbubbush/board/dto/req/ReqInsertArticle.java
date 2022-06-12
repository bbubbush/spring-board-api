package com.bbubbush.board.dto.req;

import com.bbubbush.board.annotation.DuplicateListValue;
import com.bbubbush.board.annotation.ExcelColumn;
import com.bbubbush.board.vo.board.BoardTagVO;
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

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Getter @Setter
@NoArgsConstructor
@SuperBuilder
public class ReqInsertArticle {

  @NotBlank(message = "INVALID_PARAMETER")
  @Schema(description = "제목", example = "Hello world!")
  @ExcelColumn(header = "제목")
  private String subject;

  @NotBlank(message = "INVALID_PARAMETER") @Size(max = 200, message = "TOO_LONG_TEXT")
  @Schema(description = "내용", example = "Learning mate")
  @ExcelColumn(header = "내용")
  private String text;

  @Schema(description = "작성자", example = "bbubbush")
  @ExcelColumn(header = "작성자")
  private String writer;

  @Builder.Default
  @Size(max = 5, message = "TOO_MANY_COLLECTION") @DuplicateListValue
  @Schema(description = "해시태그", example = "[\"Spring\", \"Java\"]")
  @ExcelColumn(header = "태그")
  private List<String> tags = new ArrayList<>();

  @Schema(accessMode = READ_ONLY)
  private Long targetArticleId;

  public List<BoardTagVO> convertBoardTagVO() {
    return tags.stream().map(tag -> new BoardTagVO(targetArticleId, tag, writer))
      .collect(Collectors.toList());
  }

}
