package com.bbubbush.board.api;

import com.bbubbush.board.dto.common.ReqSendMail;
import com.bbubbush.board.dto.req.ReqDeleteArticle;
import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqSearchArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.dto.res.ResSearchArticle;
import com.bbubbush.board.service.BoardService;
import com.bbubbush.board.service.MailService;
import com.bbubbush.board.vo.common.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BoardRestControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private BoardService boardService;
  @MockBean
  private MailService mailService;
  private MockMvc mockMvc;
  private final String EXPECTED_CONTENT_TYPE = MimeTypeUtils.APPLICATION_JSON_VALUE + ";charset=UTF-8";

  private final String BASE_ENDPOINT = "/board";

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
      .addFilters(new CharacterEncodingFilter("UTF-8", true))
      .alwaysDo(print())
      .build();
  }

  @Test
  void findArticle() throws Exception {
    // given
    final ReqSearchArticle reqSearchArticle = new ReqSearchArticle(1L);
    final ResSearchArticle responseArticle = createArticle();
    final ResponseVO<Object> responseVo = createResponseVo(responseArticle);
    given(boardService.findArticle(reqSearchArticle.getId())).willReturn(responseArticle);
    doNothing().when(mailService).sendMail(creatReqSendMail());

    // when

    // then
    this.mockMvc.perform(
      get(BASE_ENDPOINT + "/one")
        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(reqSearchArticle)))
      .andDo(print())
      .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
      .andExpect(content().string(objectMapper.writeValueAsString(responseVo)))
      .andExpect(status().isOk());
  }

  @Test
  void findArticles() throws Exception {
    // given
    final List<ResSearchArticle> responseArticles = createArticles();
    final ResponseVO<Object> responseVo = createResponseVo(responseArticles);
    given(boardService.findArticles()).willReturn(responseArticles);
    doNothing().when(mailService).sendMail(creatReqSendMail());

    // when

    // then
    this.mockMvc.perform(
      get(BASE_ENDPOINT + "/list", "")
        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE))
      .andDo(print())
      .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
      .andExpect(content().string(objectMapper.writeValueAsString(responseVo)))
      .andExpect(status().isOk());
  }

  @Test
  void insertArticle() throws Exception {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();
    final int expectedInsertRows = 1;
    final ResponseVO<Object> responseVo = createResponseVo(expectedInsertRows);
    given(boardService.insertArticle(any())).willReturn(expectedInsertRows);
    doNothing().when(mailService).sendMail(creatReqSendMail());

    // when

    // then
    this.mockMvc.perform(
        post(BASE_ENDPOINT + "/add")
          .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
          .content(objectMapper.writeValueAsString(reqInsertArticle)))
      .andDo(print())
      .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
      .andExpect(content().string(objectMapper.writeValueAsString(responseVo)))
      .andExpect(status().isOk());
  }

  @Test
  void updateArticle() throws Exception {
    // given
    final ReqUpdateArticle reqUpdateArticle = createReqUpdateArticle();
    final int expectedInsertRows = 1;
    final ResponseVO<Object> responseVo = createResponseVo(expectedInsertRows);
    given(boardService.updateArticle(any())).willReturn(expectedInsertRows);
    doNothing().when(mailService).sendMail(creatReqSendMail());

    // when

    // then
    this.mockMvc.perform(
        put(BASE_ENDPOINT + "/update")
          .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
          .content(objectMapper.writeValueAsString(reqUpdateArticle)))
      .andDo(print())
      .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
      .andExpect(content().string(objectMapper.writeValueAsString(responseVo)))
      .andExpect(status().isOk());
  }

  @Test
  void deleteArticle() throws Exception {
    // given
    final ReqDeleteArticle reqDeleteArticle = createReqDeleteArticle();
    final int expectedInsertRows = 1;
    final ResponseVO<Object> responseVo = createResponseVo(expectedInsertRows);
    given(boardService.deleteArticle(any())).willReturn(expectedInsertRows);
    doNothing().when(mailService).sendMail(creatReqSendMail());

    // when

    // then
    this.mockMvc.perform(
        delete(BASE_ENDPOINT + "/delete")
          .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
          .content(objectMapper.writeValueAsString(reqDeleteArticle)))
      .andDo(print())
      .andExpect(content().contentType(EXPECTED_CONTENT_TYPE))
      .andExpect(content().string(objectMapper.writeValueAsString(responseVo)))
      .andExpect(status().isOk());
  }

  private ReqInsertArticle createReqInsertArticle() {
    return ReqInsertArticle
      .builder()
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

  private ReqDeleteArticle createReqDeleteArticle() {
    return new ReqDeleteArticle(1L);
  }

  private List<String> createTagNames() {
    return new ArrayList<>() {{
      add("Spring");
      add("Java");
      add("Docker");
    }};
  }

  private <T> ResponseVO<Object> createResponseVo(T data) {
    return ResponseVO
      .builder()
      .errorCode(200)
      .errorMsg("")
      .data(data)
      .build();
  }

  private ResSearchArticle createArticle() {
    return ResSearchArticle
      .builder()
      .articleId(1L)
      .subject("Hello Spring")
      .text("자바는 자바자바해")
      .writer("뿌뿌쉬")
      .createdDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0))
      .lastModifiedDate(LocalDateTime.now())
      .build();
  }

  private List<ResSearchArticle> createArticles() {
    return new ArrayList<>(){{
      add(createArticle());
    }};
  }

  private ReqSendMail creatReqSendMail() {
    return ReqSendMail.builder()
      .from("upskilling@bbubbush.com")
      .to("upskilling@bbubbush.com")
      .subject("Hello world")
      .text("sample mail")
      .build();
  }

}