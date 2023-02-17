package com.example.Reactive_Programming.controller;

import com.example.Reactive_Programming.model.Review;
import com.example.Reactive_Programming.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Review> createReview(
            @RequestBody Review review
    ){

        return service.createReview(review);
    }

    @GetMapping("{id}")
    public Flux<Review> getReview(
            @PathVariable Integer id
    ){
        return service.getReview(id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteReview(
            @PathVariable Integer id
    ){
        return service.deleteReview(id);
    }
}
