package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.repo.BookInfoRepo;
import com.example.Reactive_Programming.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bookinfo")
public class BookInfoController {
    @Autowired
    private BookInfoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookInfo> createBook(
            @RequestBody BookInfo book
    ){
        return service.createBook(book);
    }

    @GetMapping
    public Flux<BookInfo> getAllBooks(){
        return service.getBooks();
    }
}
