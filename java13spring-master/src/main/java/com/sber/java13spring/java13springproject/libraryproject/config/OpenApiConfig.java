package com.sber.java13spring.java13springproject.libraryproject.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
      name = "Bearer Authentication",
      type = SecuritySchemeType.HTTP,
      bearerFormat = "JWT",
      scheme = "bearer"
)
public class OpenApiConfig {
    //http://localhost:9090/api/rest/swagger-ui/index.html
    @Bean
    public OpenAPI libraryProject() {
        return new OpenAPI()
              .info(new Info()
                          .title("Онлайн библиотека")
                          .description("Сервис, позволяющий арендовать книгу в онлайн библиотеке.")
                          .version("v0.1")
                          .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                          .contact(new Contact().name("Andrei A. Gavrilov")
                                         .email("volirvag132005@bk.ru")
                                         .url(""))
                   );
    }
}
