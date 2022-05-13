package com.bbubbush.board.service;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.mapper.BoardMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class BoardServiceTest {

  @InjectMocks
  private BoardService boardService;
  @Mock
  private BoardMapper boardMapper;


  @Test
  @DisplayName("게시글 조회")
  void findArticle() {
    // given
    final Long expectedId = 4L;
    given(boardMapper.findArticle(expectedId)).willReturn(createSearchArticle());
    given(boardMapper.findArticleTags(any())).willReturn(createTagNames());

    // when
    final ResSearchArticle findArticle = boardService.findArticle(expectedId);

    // then
    assertThat(findArticle).isNotNull();
    assertThat(findArticle.getArticleId())
      .isNotNull()
      .isEqualTo(expectedId);
    assertThat(findArticle.getSubject())
      .isNotNull()
      .hasSizeGreaterThan(0)
      .hasSizeLessThan(100);
    assertThat(findArticle.getText())
      .isNotNull()
      .hasSizeGreaterThan(0)
      .hasSizeLessThan(100);
    assertThat(findArticle.getTag())
      .isNotNull()
      .hasSize(3)
      .isEqualTo(createTagNames());
    assertThat(findArticle.getWriter())
      .isNotNull()
      .hasSizeGreaterThan(0)
      .hasSizeLessThan(100);
    assertThat(findArticle.getCreatedDate())
      .isNotNull()
      .isBefore(LocalDateTime.now());
    assertThat(findArticle.getLastModifiedDate())
      .isNotNull()
      .isBefore(LocalDateTime.now());
  }

  @Test
  @DisplayName("게시글 목록 조회")
  void findArticles() {
    given(boardMapper.findArticles()).willReturn(createSearchArticles());
    given(boardMapper.findArticleTags(any())).willReturn(createTagNames());

    // when
    final List<ResSearchArticle> findArticles = boardService.findArticles();

    // then
    assertThat(findArticles)
      .isNotNull()
      .hasSize(3);
  }

  @Test
  @DisplayName("게시글등록_성공")
  void insertArticle() {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();
    given(boardMapper.insertArticle(reqInsertArticle)).willReturn(1);
    given(boardMapper.insertArticleTags(reqInsertArticle)).willReturn(3);

    // when
    final int updateRows = boardService.insertArticle(reqInsertArticle);

    // then
    assertThat(updateRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글변경_성공")
  void updateArticle() {
    // given
    final Long expectedId = 4L;
    final ReqUpdateArticle reqUpdateArticle = createReqUpdateArticle();
    given(boardMapper.deleteArticleTags(expectedId)).willReturn(3);
    given(boardMapper.updateArticle(reqUpdateArticle)).willReturn(1);
    given(boardMapper.insertArticleTags(reqUpdateArticle)).willReturn(3);

    // when
    final int updateRows = boardService.updateArticle(reqUpdateArticle);

    // then
    assertThat(updateRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글삭제_성공")
  void deleteArticle() {
    // given
    final Long expectedId = 4L;
    given(boardMapper.deleteArticleTags(expectedId)).willReturn(3);
    given(boardMapper.deleteArticle(expectedId)).willReturn(1);

    // when
    final int deleteRows = boardService.deleteArticle(expectedId);

    // then
    assertThat(deleteRows).isEqualTo(1);
  }

  private ResSearchArticle createSearchArticle() {
    return ResSearchArticle
      .builder()
      .articleId(4L)
      .subject("Hello Spring")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .createdDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
      .lastModifiedDate(LocalDateTime.now())
      .build();
  }

  private List<String> createTagNames() {
    return new ArrayList<>() {{
      add("Spring");
      add("Java");
      add("Docker");
    }};
  }

  private List<ResSearchArticle> createSearchArticles() {
    return new ArrayList<>() {{
      add(createSearchArticle());
      add(createSearchArticle());
      add(createSearchArticle());
    }};
  }

  private ReqInsertArticle createReqInsertArticle() {
    return ReqInsertArticle
      .builder()
      .targetArticleId(4L)
      .subject("Hello Spring")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .tags(createTagNames())
      .build();
  }

  private ReqUpdateArticle createReqUpdateArticle() {
    return ReqUpdateArticle
      .builder()
      .id(4L)
      .targetArticleId(4L)
      .subject("자바는 자바자바해")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .tags(createTagNames())
      .build();
  }

}