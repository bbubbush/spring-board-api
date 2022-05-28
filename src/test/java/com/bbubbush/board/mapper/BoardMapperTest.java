package com.bbubbush.board.mapper;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqSearchArticle;
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

import static com.bbubbush.board.entity.NoticeBoardSqlSupport.*;
import static com.bbubbush.board.entity.NoticeBoardTagSqlSupport.*;
import static org.assertj.core.api.Assertions.assertThat;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardMapperTest {

  @Autowired
  private BoardMapper boardMapper;
  @Autowired
  private BoardTagMapper boardTagMapper;

  private final Long EXPECTED_ID = 1L;

  @Test
  @DisplayName("게시글 조회")
  void findArticle() {
    // given

    // when
    final ResSearchArticle findArticle = boardMapper.findArticle(findArticleProvider(EXPECTED_ID));

    // then
    assertThat(findArticle).isNotNull();
    assertThat(findArticle.getArticleId())
      .isNotNull()
      .isEqualTo(EXPECTED_ID);
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

    // when
    final List<ResSearchArticle> findArticles = boardMapper.findArticles(findArticlesProvider());

    // then
    assertThat(findArticles)
      .isNotNull()
      .hasSizeGreaterThan(0)
      .extracting(ResSearchArticle::getSubject)
      .usingRecursiveComparison()
      .isNotNull();
  }

  @Test
  @DisplayName("게시글등록_성공")
  void insertArticle() {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();

    // when
    final int insertRows = boardMapper.insertArticle(insertArticleProvider(reqInsertArticle));

    // then
    assertThat(insertRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글변경_성공")
  void updateArticle() {
    // given
    final ReqUpdateArticle reqUpdateArticle = createReqUpdateArticle();

    // when
    final int updateRows = boardMapper.updateArticle(updateArticleProvider(reqUpdateArticle));

    // then
    assertThat(updateRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글삭제_성공")
  void deleteArticle() {
    // given
    boardTagMapper.deleteArticleTags(deleteArticleTagsProvider(EXPECTED_ID));

    // when
    final int deleteRows = boardMapper.deleteArticle(deleteArticleProvider(EXPECTED_ID));

    // then
    assertThat(deleteRows).isEqualTo(1);
  }

  @Test
  @DisplayName("태그목록조회")
  void findArticleTags() {
    // given
    final List<String> expectedTagNames = createReqTags();

    // when
    final List<String> findTags = boardTagMapper.findArticleTags(findArticleTagsProvider(EXPECTED_ID));

    // then
    assertThat(findTags)
      .isNotNull()
      .hasSize(1)
      .isEqualTo(expectedTagNames);
  }

  @Test
  @DisplayName("게시글 연관 태그 여러개 등록")
  void insertArticleTags() {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();

    // when
    final int insertRows = boardTagMapper.insertArticleTags(insertArticleTagsProvider(reqInsertArticle));

    // then
    assertThat(insertRows).isEqualTo(1);
  }

  @Test
  @DisplayName("게시글 연관 태그 삭제")
  void deleteArticleTags() {
    // given

    // when
    final int deleteRows = boardTagMapper.deleteArticleTags(deleteArticleTagsProvider(EXPECTED_ID));

    // then
    assertThat(deleteRows).isEqualTo(1);
  }

  @Test
  @DisplayName("마이바티스3 동적 sql 테스트")
  void selectNoticeByMybatis3() {
    // given
    final ReqSearchArticle reqSearchArticle = new ReqSearchArticle(1L);

    // when
    final ResSearchArticle findArticle = boardMapper.findArticle(findArticleProvider(reqSearchArticle.getId()));

    // then
    assertThat(findArticle).isNotNull();
    assertThat(findArticle.getText())
      .isNotNull()
      .hasSizeGreaterThan(0);
    assertThat(findArticle.getCreatedDate())
      .isNotNull()
      .isBefore(LocalDateTime.now());
  }



  private List<String> createReqTags() {
    return new ArrayList<>(){{
      add("Docker");
    }};
  }

  private ReqInsertArticle createReqInsertArticle() {
    return ReqInsertArticle
      .builder()
      .targetArticleId(EXPECTED_ID)
      .subject("Hello Spring")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .tags(createReqTags())
      .build();
  }

  private ReqUpdateArticle createReqUpdateArticle() {
    return ReqUpdateArticle
      .builder()
      .id(EXPECTED_ID)
      .targetArticleId(EXPECTED_ID)
      .subject("자바는 자바자바해")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .tags(createReqTags())
      .build();
  }
}