package com.karolina.shelter.home;


import com.karolina.shelter.home.domain.Cat;
import com.karolina.shelter.home.web.GreetingWebClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collection;

import static com.karolina.shelter.home.domain.Age.ADULT;
import static com.karolina.shelter.home.domain.Age.KITTEN;
import static com.karolina.shelter.home.domain.Age.SENIOR;
import static com.karolina.shelter.home.domain.CoatColor.*;
import static com.karolina.shelter.home.domain.CoatType.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        GreetingWebClient gwc = new GreetingWebClient();

        System.out.println(gwc.startTesting());
    }

    @Bean
    public CommandLineRunner demoData(ReactiveMongoTemplate reactiveMongoTemplate) {
        return args -> {
            Flux.fromIterable(receiveInitialCats())
                    .flatMap(reactiveMongoTemplate::save)
                    .blockLast();
        };
    }

    private Collection<Cat> receiveInitialCats() {
        Collection<Cat> showsMap = new ArrayList<>();
        showsMap.add(new Cat("Mania", BROWN, BOLD, SENIOR, false, true));
        showsMap.add(new Cat("Linda", CINNAMON, LONG_HEARD, ADULT, true, true));
        showsMap.add(new Cat("Pusia", BLACK, LONG_HEARD, SENIOR, true, false));
        showsMap.add(new Cat("Roxy", CREAM, SHORT_HEARD, KITTEN, true, false));
        return showsMap;
    }
}
