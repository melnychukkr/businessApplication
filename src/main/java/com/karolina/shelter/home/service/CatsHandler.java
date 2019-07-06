package com.karolina.shelter.home.service;

import com.karolina.shelter.home.domain.Cat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.karolina.shelter.home.util.ReactiveLoggerSingleton.getReactiveLogger;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
public class CatsHandler {
    private static final Logger logger = LoggerFactory.getLogger(CatsHandler.class);
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public CatsHandler(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<ServerResponse> findCatById(ServerRequest request) {
        String id = request.pathVariables().containsKey("id") ? request.pathVariable("id") : "0";
        logger.info("access to: " + id);
        return ok()
                .contentType(APPLICATION_JSON_UTF8)
                .body(reactiveMongoTemplate.findById(id, Cat.class), Cat.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_STREAM_JSON)
                .body(reactiveMongoTemplate.findAll(Cat.class), Cat.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request
                .bodyToMono(Cat.class)
                .flatMap(reactiveMongoTemplate::save)
                .doOnEach(
                        getReactiveLogger()
                        .logOnNext(i -> logger.warn("The following object has been saved: " + i))
                )
                .flatMap(cat -> ok().body(fromObject(cat)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
            String idToRemove = request.pathVariable("id");
            logger.info("Start removing cat with id: " + idToRemove);

            Query qry = query(where("id").in(Long.valueOf(idToRemove)));
            return ok()
                    .body(reactiveMongoTemplate.findAllAndRemove(qry, Cat.class), Cat.class);
    }
}
