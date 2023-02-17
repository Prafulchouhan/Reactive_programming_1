package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.Review;
import com.example.Reactive_Programming.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@WebFluxTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ReviewService reviewService;

    Flux<Review> reviewFlux;

    Mono<Review> reviewMono;

    @BeforeEach
    void setUp() {
        reviewFlux=Flux.just(new Review(1,1,1,"comment1")
        ,new Review(2,1,2,"comment2"));
        reviewMono=Mono.just(new Review(1,1,1,"comment1"));
    }

    @Test
    void createReview() {
        Mockito.when(reviewService.createReview(Mockito.any(Review.class)))
                .thenReturn(reviewMono);
        webTestClient.post()
                .uri("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(reviewMono),Review.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Review.class)
                .value(
                        e->assertEquals(1,e.getBookId())
                );
    }

    @Test
    void getReview() {

        Mockito.when(reviewService.getReview(Mockito.anyInt())).thenReturn(reviewFlux);
        webTestClient.get()
                .uri("/review/{id}",1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Review.class)
                .value(
                        e->assertEquals(1,e.get(0).getBookId())
                );
    }

    @Test
    void deleteReview() {
        Mono<Void> mono=Mono.empty();
        Mockito.when(reviewService.deleteReview(Mockito.anyInt())).thenReturn(mono);
        webTestClient.delete()
                .uri("/review/delete/{id}",1)
                .exchange()
                .expectStatus().isOk();
    }
}