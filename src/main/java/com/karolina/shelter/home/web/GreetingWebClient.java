package com.karolina.shelter.home.web;

import com.karolina.shelter.home.domain.Age;
import com.karolina.shelter.home.domain.Cat;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import static com.karolina.shelter.home.domain.CoatColor.BROWN;
import static com.karolina.shelter.home.domain.CoatType.BOLD;

public class GreetingWebClient {
    private WebClient client = WebClient
            .builder()
            .clientConnector(new ReactorClientHttpConnector())
            .baseUrl("http://localhost:8080")
            .build();

    public String startTesting() {


        Cat catToPost = new Cat("AddedByWebClient", BROWN, BOLD, Age.KITTEN, false, true);
        
        client.post()
                .uri("/cat")
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(catToPost)
                .retrieve()
                .bodyToMono(Cat.class)
                .thenMany(client.get()
                        .uri("/cat")
                        .retrieve()
                        .bodyToFlux(Cat.class)
                        .limitRequest(5))
                .subscribe(this::printCats);

//                .block(Duration.ofSeconds(2));
//                .onStatus(httpStatus -> true, clientResponse -> {
//                    System.out.println(clientResponse);
//                    return Mono.empty();
//                });
/*

                client.get()
                        .uri("/cat")
    //                .accept(APPLICATION_STREAM_JSON)
                        .retrieve()
                        .bodyToFlux(Cat.class)
                        .limitRequest(3)
                        .subscribe(this::printCats);



//        client.get(
//                .uri("/cat")
//                .retrieve()
//                .bodyToFlux(Cat.class)
//                .limitRequest(3)
//                .subscribe(this::printCats);

        /*.retrieve()
        .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> {
            client.get()
                    .uri("/cat/7878")
    //                .accept(APPLICATION_STREAM_JSON)
                    .retrieve()
                    .bodyToMono(Cat.class)
                    .subscribe(this::printCats);

            return Mono.empty();
        });*/
//                .accept(APPLICATION_STREAM_JSON)

        return ">> result = "/* + cats.block()*/;
    }

    private void printCats(Cat i) {
        System.out.println("===== Cats ===== \n" + i.getId());
//        System.out.println("===== Cats ===== \n"+i.getName()+" "+i.getCoatColor());
    }
}
