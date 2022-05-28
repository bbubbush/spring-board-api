package com.bbubbush.board.api;

import com.bbubbush.board.dto.req.ReqDeleteArticle;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqSearchArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.util.ApiResponse;
import com.bbubbush.board.vo.common.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/one")
  public ResponseVO findArticle(@Valid @RequestBody ReqSearchArticle reqSearchArticle) {
    return ApiResponse.success(boardService.findArticle(reqSearchArticle.getId()));
  }

  @GetMapping("/list")
  public ResponseVO findArticles() {
    return ApiResponse.success(boardService.findArticles());
  }

  @PostMapping("/add")
  public ResponseVO insertArticle(@Valid @RequestBody ReqInsertArticle reqInsertArticle) {
    return ApiResponse.success(boardService.insertArticle(reqInsertArticle));
  }

  @PutMapping("/update")
  public ResponseVO updateArticle(@Valid @RequestBody ReqUpdateArticle reqUpdateArticle) {
    return ApiResponse.success(boardService.updateArticle(reqUpdateArticle));
  }

  @DeleteMapping("/delete")
  public ResponseVO deleteArticle(@Valid @RequestBody ReqDeleteArticle reqModifyArticle) {
    return ApiResponse.success(boardService.deleteArticle(reqModifyArticle.getId()));
  }

}
