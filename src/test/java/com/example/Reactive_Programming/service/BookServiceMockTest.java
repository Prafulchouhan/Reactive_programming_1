package com.example.Reactive_Programming.service;

import com.example.Reactive_Programming.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {
    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;
    @InjectMocks
    private BookService bookService;


    @Test
    void getBooks() {

        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReview(Mockito.anyInt())).thenCallRealMethod();
        var books=bookService.getBooks();
        StepVerifier.create(books)
                .expectNextCount(3)
                .verifyComplete();

    }

    @Test
    void getBooksMockOnError() {

        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReview(Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("error occurred !!"));

        var books=bookService.getBooks();
        StepVerifier.create(books)
                .expectError(BookNotFoundException.class)
                .verify();

    }


    @Test
    void getBooksMockOnErrorRetry() {

        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReview(Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("error occurred !!"));

        var books=bookService.getBooksRetry();
        StepVerifier.create(books)
                .expectError(BookNotFoundException.class)
                .verify();

    }


    @Test
    void getBooksMockOnErrorRetryWhen() {

        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReview(Mockito.anyInt()))
                .thenThrow(new IllegalArgumentException("error occurred !!"));

        var books=bookService.getBooksRetryWhen();
        StepVerifier.create(books)
                .expectError(BookNotFoundException.class)
                .verify();

    }
}