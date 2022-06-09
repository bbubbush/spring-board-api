package com.bbubbush.board.api;

import com.bbubbush.board.dto.req.*;
import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "Board API", description = "게시판 API")
@Slf4j
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/one")
  @Operation(summary = "게시물 단건 조회", description = "게시물 정보를 하나만 조회합니다.", tags = "Board API")
  public ResponseVO findArticle(@Parameter(description = "게시물 번호", example = "11")
                                  @Valid ReqSearchArticle reqSearchArticle) {
    return ApiResponse.success(boardService.findArticle(reqSearchArticle.getId()));
  }

  @GetMapping("/list")
  @Operation(summary = "전체 게시물 조회", description = "등록된 게시물 정보 전체를 조회합니다.", tags = "Board API")
  public ResponseVO findArticles() {
    return ApiResponse.success(boardService.findArticles());
  }

  @PostMapping("/add")
  @Operation(summary = "게시물 등록", description = "게시물을 등록합니다.", tags = "Board API")
  public ResponseVO insertArticle(@Valid @RequestBody ReqInsertArticle reqInsertArticle) {
    return ApiResponse.success(boardService.insertArticle(reqInsertArticle));
  }

  @PutMapping("/update")
  @Operation(summary = "게시물 수정", description = "게시물 정보를 변경합니다.", tags = "Board API")
  public ResponseVO updateArticle(@Valid @RequestBody ReqUpdateArticle reqUpdateArticle) {
    return ApiResponse.success(boardService.updateArticle(reqUpdateArticle));
  }

  @DeleteMapping("/delete")
  @Operation(summary = "게시물 삭제", description = "게시물정보를 삭제합니다.", tags = "Board API")
  public ResponseVO deleteArticle(@Valid @RequestBody ReqDeleteArticle reqDeleteArticle) {
    return ApiResponse.success(boardService.deleteArticle(reqDeleteArticle.getId()));
  }

  @PostMapping(value = "/upload/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "게시물 등록", description = "게시물을 등록합니다.")
  public ResponseVO uploadExcel(ReqExcelUploadArticle reqExcelUploadArticle) {
    final MultipartFile uploadFile = reqExcelUploadArticle.getUploadFile();
    log.info("uploadFile :: {}", uploadFile.getSize());
    return ApiResponse.success("success");
  }

  @GetMapping(value = "/download/excel")
  @Operation(summary = "게시물 등록", description = "게시물을 등록합니다.")
  public ResponseVO downloadExcel() {
    return ApiResponse.success("success");
  }

}
