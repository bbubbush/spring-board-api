package com.bbubbush.board.config;

import com.bbubbush.board.vo.common.ResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Collections.singletonList;

@EnableSwagger2
@Component
@RequiredArgsConstructor
public class SwaggerConfig {

  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.bbubbush.board"))
      .paths(PathSelectors.any())
      .build()
//      .pathMapping("/")
//      .directModelSubstitute(LocalDate.class, String.class)
      .genericModelSubstitutes(ResponseVO.class)
      .useDefaultResponseMessages(false)
      .securitySchemes(singletonList(apiKey()))
      .securityContexts(singletonList(securityContext()))
    ;
  }

  private ApiKey apiKey() {
    return new ApiKey("basic-user", "Bearer", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
      .securityReferences(defaultAuth())
      .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
      = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return singletonList(
      new SecurityReference("basic-user", authorizationScopes));
  }

}
