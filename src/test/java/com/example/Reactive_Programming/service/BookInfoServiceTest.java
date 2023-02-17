package com.example.Reactive_Programming.service;

import com.example.Reactive_Programming.model.BookInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookInfoServiceTest {
    @Mock
    private BookInfoService service;



    @Autowired
    private WebTestClient webTestClient;

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
    void createBook() {

        Mockito.when(service.createBook(Mockito.any(BookInfo.class))).thenReturn(bookInfoMono);
//        Mockito.doReturn(bookInfo).when(service.createBook(Mockito.any(BookInfo.class)));
        var bookinfo = service.createBook(bookInfoMono.block()).log();
        StepVerifier.create(bookinfo)
                .assertNext(bookInfo -> {
                    assertEquals(1, bookInfo.getId());
                })
                .verifyComplete();

    }
    @Test
    void getBooks() {
        Mockito.when(service.getBooks())
                .thenReturn(bookInfoFlux);
        var data=service.getBooks();

        StepVerifier.create(data)
                .assertNext(e->{
                    assertEquals(1,e.getId());
                    assertEquals("title1",e.getTitle());
                })
                .assertNext(e->{
                    assertEquals(2,e.getId());
                    assertEquals("title2",e.getTitle());
                })
                .assertNext(e->{
                    assertEquals(3,e.getId());
                    assertEquals("title3",e.getTitle());
                })
                .verifyComplete();
    }

    @Test
    void getBookById() {
        Mockito.when(service.getBookById(Mockito.anyInt())).thenReturn(bookInfoMono);
//        Mockito.doReturn(bookInfoMono).when(service.getBookById(Mockito.anyInt()));
        var bookinfo = service.getBookById(Mockito.anyInt()).log();
        StepVerifier.create(bookinfo)
                .assertNext(bookInfo -> {
                    assertEquals(1, bookInfo.getId());
                })
                .verifyComplete();
    }

    @Test
    void deleteBookById() {
    }

    @Test
    void updateBook() {
        Mockito.when(service.createBook(Mockito.any(BookInfo.class))).thenReturn(bookInfoMono);
//        Mockito.doReturn(bookInfo).when(service.createBook(Mockito.any(BookInfo.class)));
        var bookinfo = service.createBook(bookInfoMono.block()).log();
        StepVerifier.create(bookinfo)
                .assertNext(bookInfo -> {
                    assertEquals(1, bookInfo.getId());
                })
                .verifyComplete();
    }
}