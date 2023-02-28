package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.repo.BookInfoRepo;
import com.example.Reactive_Programming.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

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

    @GetMapping(value = "",produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<BookInfo> getAllBooks(){
        return service.getBooks().delayElements(Duration.ofSeconds(1));
    }
}
/*
@RestController
@RequestMapping("/bookinfo")
public class BookInfoController {
    @Autowired
    private BookInfoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookInfo createBook(
            @RequestBody BookInfo book
    ){
        return service.createBook(book);
    }

    @GetMapping(value = "")
    public List<BookInfo> getAllBooks(){
        return service.getBooks();
    }
}
 */
