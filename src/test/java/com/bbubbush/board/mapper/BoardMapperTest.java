package com.bbubbush.board.mapper;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardMapperTest {

  @Autowired
  private BoardMapper boardMapper;

  @Test
  @DisplayName("게시글 조회")
  void findArticle() {
    // given
    final Long expectedId = 4L;

    // when
    final ResSearchArticle findArticle = boardMapper.findArticle(expectedId);

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
      .hasSize(0);
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
    // given
    final Long expectedId = 4L;
    final ResSearchArticle findArticle = boardMapper.findArticle(expectedId);

    // when
    final List<ResSearchArticle> findArticles = boardMapper.findArticles();

    // then
    assertThat(findArticles)
      .isNotNull()
      .hasSizeGreaterThan(0)
      .filteredOn(resSearchArticle -> Objects.equals(resSearchArticle.getArticleId(), findArticle.getArticleId()));
  }

  @Test
  @DisplayName("게시글등록_성공")
  void insertArticle() {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();

    // when
    final int insertRows = boardMapper.insertArticle(reqInsertArticle);

    // then
    assertThat(insertRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글변경_성공")
  void updateArticle() {
    // given
    final ReqUpdateArticle reqUpdateArticle = createReqUpdateArticle();

    // when
    final int updateRows = boardMapper.updateArticle(reqUpdateArticle);

    // then
    assertThat(updateRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글삭제_성공")
  void deleteArticle() {
    // given
    final Long expectedId = 4L;

    // when
    final int deleteRows = boardMapper.deleteArticle(expectedId);

    // then
    assertThat(deleteRows).isEqualTo(1);
  }

  @Test
  @DisplayName("태그목록조회")
  void findArticleTags() {
    // given
    final Long expectedId = 17L;
    final List<String> expectedTagNames = createReqTags();

    // when
    final List<String> findTags = boardMapper.findArticleTags(expectedId);

    // then
    assertThat(findTags)
      .isNotNull()
      .hasSize(3)
      .isEqualTo(expectedTagNames);
  }

  @Test
  @DisplayName("게시글 연관 태그 여러개 등록")
  void insertArticleTags() {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();

    // when
    final int insertRows = boardMapper.insertArticleTags(reqInsertArticle);

    // then
    assertThat(insertRows).isEqualTo(3);
  }

  @Test
  @DisplayName("게시글 연관 태그 삭제")
  void deleteArticleTags() {
    // given
    final Long expectedId = 17L;

    // when
    final int deleteRows = boardMapper.deleteArticleTags(expectedId);

    // then
    assertThat(deleteRows).isEqualTo(3);
  }

  private List<String> createReqTags() {
    return new ArrayList<>(){{
      add("Spring");
      add("Java");
      add("Docker");
    }};
  }

  private ReqInsertArticle createReqInsertArticle() {
    return ReqInsertArticle
      .builder()
      .targetArticleId(4L)
      .subject("Hello Spring")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .tags(createReqTags())
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
      .tags(createReqTags())
      .build();
  }
}