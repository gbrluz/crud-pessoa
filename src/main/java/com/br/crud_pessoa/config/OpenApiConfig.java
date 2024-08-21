package com.br.crud_pessoa.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Pessoas e Endereços")
                        .version("1.0.0")
                        .description("Documentação da API de Gerenciamento de Pessoas e Endereços")
                        .contact(new Contact().name("Gabriel").email("gabriel.luz@dbserver.com.br")));
    }
}
