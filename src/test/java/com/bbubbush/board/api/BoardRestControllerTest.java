package com.bbubbush.board.api;

import com.bbubbush.board.dto.req.ReqInsertArticle;
import com.bbubbush.board.dto.req.ReqUpdateArticle;
import com.bbubbush.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
  private MockMvc mockMvc;

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

    // when

    // then
    this.mockMvc.perform(
      get(BASE_ENDPOINT + "/one")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\": 4}"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void findArticles() throws Exception {
    // given

    // when

    // then
    this.mockMvc.perform(
      get(BASE_ENDPOINT + "/list", "")
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void insertArticle() throws Exception {
    // given
    final ReqInsertArticle reqInsertArticle = createReqInsertArticle();

    // when

    // then
    this.mockMvc.perform(
        post(BASE_ENDPOINT + "/add")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(reqInsertArticle)))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void updateArticle() throws Exception {
    // given
    final ReqUpdateArticle reqUpdateArticle = createReqUpdateArticle();

    // when

    // then
    this.mockMvc.perform(
        put(BASE_ENDPOINT + "/update")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(reqUpdateArticle)))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  void deleteArticle() throws Exception {
    // given

    // when

    // then
    this.mockMvc.perform(
        delete(BASE_ENDPOINT + "/delete")
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"id\": 4}"))
      .andDo(print())
      .andExpect(status().isOk());
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

  private List<String> createTagNames() {
    return new ArrayList<>() {{
      add("Spring");
      add("Java");
      add("Docker");
    }};
  }

}