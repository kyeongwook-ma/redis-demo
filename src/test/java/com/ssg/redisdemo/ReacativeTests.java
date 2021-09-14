package com.ssg.redisdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;
import java.util.function.Predicate;

public class ReacativeTests {

    @Test
    void test_flux_filter() {

        Flux<String> source = Flux.just("John", "Monica", "Mark")
                .filter(name -> name.length() >= 4)
                .map(String::toUpperCase);

        StepVerifier
                .create(source)
                .expectNext("JOHN")
                .expectNextMatches(name -> name.startsWith("MO"))
                .expectNext("MARK")
                .expectComplete()
                .verify();

    }
}
