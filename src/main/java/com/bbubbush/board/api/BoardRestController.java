package com.bbubbush.board.api;

import com.bbubbush.board.dto.req.ReqDeleteArticle;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqSearchArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/one")
  @ApiOperation(value = "게시물 단건 조회", notes = "게시물 정보를 하나만 조회합니다.")
  public ResponseVO findArticle(@Valid ReqSearchArticle reqSearchArticle) {
    return ApiResponse.success(boardService.findArticle(reqSearchArticle.getId()));
  }

  @GetMapping("/list")
  @ApiOperation(value = "전체 게시물 조회", notes = "등록된 게시물 정보 전체를 조회합니다.")
  public ResponseVO findArticles() {
    return ApiResponse.success(boardService.findArticles());
  }

  @PostMapping("/add")
  @ApiOperation(value = "게시물 등록", notes = "게시물을 등록합니다.")
  public ResponseVO insertArticle(@Valid @RequestBody ReqInsertArticle reqInsertArticle) {
    return ApiResponse.success(boardService.insertArticle(reqInsertArticle));
  }

  @PutMapping("/update")
  @ApiOperation(value = "게시물 수정", notes = "게시물 정보를 변경합니다.")
  public ResponseVO updateArticle(@Valid @RequestBody ReqUpdateArticle reqUpdateArticle) {
    return ApiResponse.success(boardService.updateArticle(reqUpdateArticle));
  }

  @DeleteMapping("/delete")
  @ApiOperation(value = "게시물 삭제", notes = "게시물정보를 삭제합니다.")
  public ResponseVO deleteArticle(@Valid @RequestBody ReqDeleteArticle reqDeleteArticle) {
    return ApiResponse.success(boardService.deleteArticle(reqDeleteArticle.getId()));
  }

}
