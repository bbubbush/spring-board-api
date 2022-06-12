package com.bbubbush.board.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResExcelUploadArticle {

  private int totalInsertRows;
  private int successInsertRows;
  private int failInsertRows;
}
