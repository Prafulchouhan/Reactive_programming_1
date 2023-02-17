package com.example.Reactive_Programming.repo;

import com.example.Reactive_Programming.model.Review;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReviewRepo extends R2dbcRepository<Review, Integer> {

    @Query("select r.id,r.book_id,r.rating,r.comments from review r where r.book_id = :bookId")
    Flux<Review> findReviewByBookId(Integer bookId);
}
