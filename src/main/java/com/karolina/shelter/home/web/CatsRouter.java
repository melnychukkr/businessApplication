package com.karolina.shelter.home.web;

import com.karolina.shelter.home.service.CatsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class CatsRouter {

    @Bean
    public RouterFunction<ServerResponse> route(CatsHandler catsHandler) {
        return RouterFunctions.route()
                .GET("/findCatById", accept(TEXT_PLAIN), catsHandler::findCatById)
                .POST("/cat", catsHandler::save)
                .GET("/cat/{id}", accept(APPLICATION_JSON_UTF8), catsHandler::findCatById)
                .DELETE("/cat/{id}", catsHandler::delete)
                .GET("/cat", accept(APPLICATION_STREAM_JSON), catsHandler::findAll)
                .build();
    }

}
 
