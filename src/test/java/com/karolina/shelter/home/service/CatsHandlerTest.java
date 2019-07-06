package com.karolina.shelter.home.service;

import com.karolina.shelter.home.domain.Cat;
import com.karolina.shelter.home.web.CatsRouter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static com.karolina.shelter.home.domain.Age.KITTEN;
import static com.karolina.shelter.home.domain.Age.SENIOR;
import static com.karolina.shelter.home.domain.CoatColor.BROWN;
import static com.karolina.shelter.home.domain.CoatColor.CINNAMON;
import static com.karolina.shelter.home.domain.CoatType.BOLD;
import static com.karolina.shelter.home.domain.CoatType.SHORT_HEARD;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@ExtendWith(MockitoExtension.class)
class CatsHandlerTest {
    
    @Mock
    private ReactiveMongoTemplate template;

    private static Cat SOME_CAT_1 = new Cat(1,"Mania", BROWN, BOLD, KITTEN, false, true);
    private static Cat SOME_CAT_2 = new Cat(2, "Pusia", CINNAMON, SHORT_HEARD, SENIOR, true, true);


    private WebTestClient client;

    @BeforeEach
    public void setUp() {
        CatsHandler catsHandler = new CatsHandler(template);
        CatsRouter router = new CatsRouter();
        client = WebTestClient.bindToRouterFunction(router.route(catsHandler)).build();
    }

    @Test
    public void shouldReturnAvailableCats() {
        when(template.findAll(Cat.class)).thenReturn(Flux.just(SOME_CAT_1));

        client.get().uri("/cat")
                .accept(APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_STREAM_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo(SOME_CAT_1.getName())
                .jsonPath("$.coatColor").isEqualTo(BROWN.name())
                .jsonPath("$.coatType").isEqualTo(BOLD.name())
                .jsonPath("$.age").isEqualTo(KITTEN.name())
                .jsonPath("$.requiresTreatment").isEqualTo(false)
                .jsonPath("$.usesCuvette").isEqualTo(true);
    }

    @Test
    public void shouldDeleteCatWithTheProperId() {
        int idToDelete = 2;
        when(template.findAllAndRemove(query(where("id").in((long) idToDelete)), Cat.class)).thenReturn(Flux.just(SOME_CAT_2));

        client.delete().uri("/cat/"+idToDelete)
                .accept(APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON_UTF8);
    }
}
