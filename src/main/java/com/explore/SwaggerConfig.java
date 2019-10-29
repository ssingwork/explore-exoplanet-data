package com.explore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.time.LocalDate;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("Data Exoplanet API").apiInfo(getApiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.explore.controllers")).paths(PathSelectors.any()).build()
        .directModelSubstitute(LocalDate.class, String.class).directModelSubstitute(Timestamp.class, String.class);
  }


  private ApiInfo getApiInfo() {
    return new ApiInfoBuilder().title("UIC Mock Server APIs")
        .description("This page will help you to go over the API documentation").version("1.0.0").build();
  }

}
