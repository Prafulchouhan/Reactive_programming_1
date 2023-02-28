package com.example.Reactive_Programming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookInfoService bookInfoService=new BookInfoService();
    private ReviewService reviewService=new ReviewService();

    private BookService bookService=new BookService(bookInfoService,reviewService);


//    @Test
//    void getBooks() {
//
//        var books=bookService.getBooks();
//        StepVerifier.create(books)
//                .assertNext(book->{
//                            assertEquals("Book One",book.getBookInfo().getTitle());
//                            assertEquals(2,book.getReviews().size());
//                        }
//                )
//                .assertNext(book->{
//                            assertEquals("Book Two",book.getBookInfo().getTitle());
//                            assertEquals(2,book.getReviews().size());
//                        }
//                )
//                .assertNext(book->{
//                            assertEquals("Book three",book.getBookInfo().getTitle());
//                            assertEquals(2,book.getReviews().size());
//                        }
//                )
//                .verifyComplete();
//    }
//
//    @Test
//    void getBookById() {
//        Integer id=1;
//        var book=bookService.getBookById(id);
//        StepVerifier.create(book)
//                .assertNext(books-> {
//                            assertEquals("Book One", books.getBookInfo().getTitle());
//                            assertEquals(2, books.getReviews().size());
//                        }
//                ).verifyComplete();
//
//    }
}