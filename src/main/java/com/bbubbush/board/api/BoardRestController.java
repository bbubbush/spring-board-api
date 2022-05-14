package com.bbubbush.board.api;

import com.bbubbush.board.dto.req.ReqDeleteArticle;
import com.bbubbush.board.dto.req.ReqSearchArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.vo.common.ResponseVO;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/one")
  public ResponseEntity<ResponseVO<ResSearchArticle>> findArticle(@RequestBody ReqSearchArticle reqSearchArticle) {
    ResponseVO<ResSearchArticle> responseVO = new ResponseVO<>();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.findArticle(reqSearchArticle.getId()));
    return ResponseEntity.ok(responseVO);
  }

  @GetMapping("/list")
  public ResponseEntity<ResponseVO<List<ResSearchArticle>>> findArticles() {
    ResponseVO<List<ResSearchArticle>> responseVO = new ResponseVO<>();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.findArticles());
    return ResponseEntity.ok(responseVO);
  }

  @PostMapping("/add")
  public ResponseEntity<ResponseVO<Integer>> insertArticle(@RequestBody ReqInsertArticle reqInsertArticle) {
    ResponseVO<Integer> responseVO = new ResponseVO<>();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.insertArticle(reqInsertArticle));
    return ResponseEntity.ok(responseVO);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseVO<Integer>> updateArticle(@RequestBody ReqUpdateArticle reqUpdateArticle) {
    ResponseVO<Integer> responseVO = new ResponseVO<>();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.updateArticle(reqUpdateArticle));
    return ResponseEntity.ok(responseVO);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseVO<Integer>> deleteArticle(@RequestBody ReqDeleteArticle reqModifyArticle) {
    ResponseVO<Integer> responseVO = new ResponseVO<>();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.deleteArticle(reqModifyArticle.getId()));
    return ResponseEntity.ok(responseVO);
  }

}
