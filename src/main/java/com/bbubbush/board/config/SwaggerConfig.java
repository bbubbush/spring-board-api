package com.bbubbush.board.config;

import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Collections.singletonList;

@EnableSwagger2
@Component
@RequiredArgsConstructor
public class SwaggerConfig {

  private final TypeResolver typeResolver;

  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
      .paths(PathSelectors.any())
      .build();
//      .pathMapping("/")
//      .directModelSubstitute(LocalDate.class, String.class)
//      .genericModelSubstitutes(ResponseEntity.class)
//      .alternateTypeRules(
//        newRule(typeResolver.resolve(DeferredResult.class,
//            typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//          typeResolver.resolve(WildcardType.class)))
//      .useDefaultResponseMessages(false)
//      .globalResponses(HttpMethod.GET,
//        singletonList(new ResponseBuilder()
//          .code("500")
//          .description("500 message")
//          .representation(MediaType.TEXT_XML)
//          .apply(r ->
//            r.model(m ->
//              m.referenceModel(ref ->
//                ref.key(k ->
//                  k.qualifiedModelName(q ->
//                    q.namespace("some:namespace")
//                      .name("ERROR"))))))
//          .build()))
////      .securitySchemes(singletonList(apiKey()))
////      .securityContexts(singletonList(securityContext()))
//      .enableUrlTemplating(true)
//      .globalRequestParameters(
//        singletonList(new springfox.documentation.builders.RequestParameterBuilder()
//          .name("someGlobalParameter")
//          .description("Description of someGlobalParameter")
//          .in(ParameterType.QUERY)
//          .required(true)
//          .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//          .build()))
//      .tags(new Tag("Pet Service", "All apis relating to pets"));
  }

  private ApiKey apiKey() {
    return new ApiKey("mykey", "api_key", "header");
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
      new SecurityReference("mykey", authorizationScopes));
  }

  @Bean
  SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
      .clientId("test-app-client-id")
      .clientSecret("test-app-client-secret")
      .realm("test-app-realm")
      .appName("test-app")
      .scopeSeparator(",")
      .additionalQueryStringParams(null)
      .useBasicAuthenticationWithAccessCodeGrant(false)
      .enableCsrfSupport(false)
      .build();
  }

  @Bean
  UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
      .deepLinking(true)
      .displayOperationId(false)
      .defaultModelsExpandDepth(1)
      .defaultModelExpandDepth(1)
      .defaultModelRendering(ModelRendering.EXAMPLE)
      .displayRequestDuration(false)
      .docExpansion(DocExpansion.NONE)
      .filter(false)
      .maxDisplayedTags(null)
      .operationsSorter(OperationsSorter.ALPHA)
      .showExtensions(false)
      .showCommonExtensions(false)
      .tagsSorter(TagsSorter.ALPHA)
      .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
      .validatorUrl(null)
      .build();
  }

}
