package com.bbubbush.board.api;

import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.vo.common.ResponseVO;
import com.bbubbush.board.vo.req.ReqInsertArticle;
import com.bbubbush.board.vo.req.ReqUpdateArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardRestController {

  private final BoardService boardService;

  @GetMapping("/one")
  public ResponseEntity<ResponseVO> findArticle(@RequestBody Long articleId) {
    ResponseVO responseVO = new ResponseVO();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.findArticle(articleId));
    return ResponseEntity.ok(responseVO);
  }

  @GetMapping("/list")
  public ResponseEntity<ResponseVO> findArticles() {
    ResponseVO responseVO = new ResponseVO();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.findArticles());
    return ResponseEntity.ok(responseVO);
  }

  @PostMapping("/add")
  public ResponseEntity<ResponseVO> insertArticle(@RequestBody ReqInsertArticle reqInsertArticle) {
    ResponseVO responseVO = new ResponseVO();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.insertArticle(reqInsertArticle));
    return ResponseEntity.ok(responseVO);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseVO> updateArticle(@RequestBody ReqUpdateArticle reqUpdateArticle) {
    ResponseVO responseVO = new ResponseVO();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.updateArticle(reqUpdateArticle));
    return ResponseEntity.ok(responseVO);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseVO> deleteArticle(@RequestBody Long articleId) {
    ResponseVO responseVO = new ResponseVO();
    responseVO.setErrorCode(200);
    responseVO.setData(boardService.deleteArticle(articleId));
    return ResponseEntity.ok(responseVO);
  }

}
