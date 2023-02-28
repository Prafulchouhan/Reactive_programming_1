package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.Book;
import com.example.Reactive_Programming.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/book/")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping(value = "/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getAllBooks(){
        return service.getBooks();
    }

    @GetMapping(value = "/{id}")
    public Mono<Book> getBookById(
            @PathVariable Integer id
    ){
        return service.getBookById(id);
    }
}

/*
@RestController
@RequestMapping("api/book/")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping(value = "/all")
    public List<Book> getAllBooks(){
        return service.getBooks();
    }

    @GetMapping(value = "/{id}")
    public Book getBookById(
            @PathVariable Integer id
    ){
        return service.getBookById(id);
    }
}
 */
