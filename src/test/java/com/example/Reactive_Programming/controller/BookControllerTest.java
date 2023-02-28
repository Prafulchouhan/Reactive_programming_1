package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.Book;
import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.model.Review;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@WebFluxTest(BookController.class)
class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService service;

    private Flux<Book> bookFlux;

    private Mono<Book> bookMono;

    @BeforeEach
    public void init() {
        bookFlux = Flux.just(new Book(new BookInfo(1, "title1", "author1", "123"),
                        List.of(new Review(1, 1, 1, "comment1")
                                , new Review(2, 1, 2, "comment2")))
                , new Book(new BookInfo(2, "title2", "author2", "1234"),
                        List.of(new Review(3, 2, 5, "comment1")
                                , new Review(4, 2, 3, "comment2"))));
        bookMono=Mono.just(new Book(new BookInfo(1, "title1", "author1", "123"),
                List.of(new Review(1, 1, 1, "comment1")
                        , new Review(2, 1, 2, "comment2"))));

    }

    @Test
    void getAllBooks() {
        Mockito.when(service.getBooks())
                .thenReturn(bookFlux);

        webTestClient.get()
                .uri("/api/book/all")
                .accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .value(e->{
                    assertEquals(1,e.get(0).getBookInfo().getId());
                    assertEquals(1,e.get(0).getReviews().get(0).getBookId());

                    assertEquals(2,e.get(1).getBookInfo().getId());
                    assertEquals(2,e.get(1).getReviews().get(0).getBookId());
                });
    }

    @Test
    void getBookById() {
        Mockito.when(service.getBookById(Mockito.anyInt()))
                .thenReturn(bookMono);

        webTestClient.get()
                .uri("/api/book/{id}",1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(e->{
                    assertEquals(1,e.getBookInfo().getId());
                    assertEquals(1,e.getReviews().get(0).getBookId());
                });

    }
}