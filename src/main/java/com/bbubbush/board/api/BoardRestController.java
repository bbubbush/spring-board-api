package com.bbubbush.board.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
public class BoardRestController {

    @GetMapping("/list")
    public ResponseEntity findBoardList() {
        return ResponseEntity.ok("Hello world!");
    }

}
