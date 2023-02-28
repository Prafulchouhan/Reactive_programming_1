package com.example.Reactive_Programming.service;

import com.example.Reactive_Programming.exception.BookNotFoundException;
import com.example.Reactive_Programming.model.Book;
import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {
    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;
    @Mock
    private BookService bookService;
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
    void getBooks() {
        Mockito.when(bookService.getBooks()).thenReturn(bookFlux);
        var books=bookService.getBooks();
        StepVerifier.create(books)
                .expectNextCount(2)
                .verifyComplete();

    }

//    @Test
//    void getBooksMockOnError() {
//
//        Mockito.when(bookService.getBooks())
//                .thenThrow(new IllegalArgumentException("error occurred !!"));
//
//        var books=bookService.getBooks();
//        StepVerifier.create(books)
//                .expectError(BookNotFoundException.class)
//                .verify();
//
//    }
//
//
//    @Test
//    void getBooksMockOnErrorRetry() {
//
//        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
//        Mockito.when(reviewService.getReview(Mockito.anyInt()))
//                .thenThrow(new IllegalArgumentException("error occurred !!"));
//
//        var books=bookService.getBooksRetry();
//        StepVerifier.create(books)
//                .expectError(BookNotFoundException.class)
//                .verify();
//
//    }
//
//
//    @Test
//    void getBooksMockOnErrorRetryWhen() {
//
//        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
//        Mockito.when(reviewService.getReview(Mockito.anyInt()))
//                .thenThrow(new IllegalArgumentException("error occurred !!"));
//
//        var books=bookService.getBooksRetryWhen();
//        StepVerifier.create(books)
//                .expectError(BookNotFoundException.class)
//                .verify();
//
//    }
}