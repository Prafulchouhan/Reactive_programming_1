package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.service.BookInfoService;
import com.example.Reactive_Programming.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(BookInfoController.class)
class BookInfoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookInfoService service;

    private Mono<BookInfo> bookInfoMono;

    private Flux<BookInfo> bookInfoFlux;

    @BeforeEach
    public void init(){
        bookInfoMono=Mono.just(new BookInfo(1,"title","auther","123"));
        bookInfoFlux=Flux.fromIterable(List.of(new BookInfo(1,"title1","auther1","123"),
                new BookInfo(2,"title2","auther2","1234"),
                new BookInfo(3,"title3","auther3","12345")));

    }

    @Test
    void getAllBooks() {
        Mockito.when(service.getBooks())
                .thenReturn(bookInfoFlux);

        webTestClient.get()
                .uri("/bookinfo")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookInfo.class)
                .value(e->
                        assertEquals(1,e.get(0).getId())
                        );
    }

    @Test
    void createBook() {
        Mockito.when(service.createBook(Mockito.any(BookInfo.class)))
                .thenReturn(bookInfoMono);

        webTestClient.post()
                .uri("/bookinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(bookInfoMono), BookInfo.class)
                .exchange()
                .expectStatus().isCreated();
    }
}