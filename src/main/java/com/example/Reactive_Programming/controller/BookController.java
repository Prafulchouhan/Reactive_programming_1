package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.Book;
import com.example.Reactive_Programming.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public Flux<Book> getAllBooks(){
        return service.getBooks();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBookById(
            @PathVariable Integer id
    ){
        return service.getBookById(id);
    }
}
