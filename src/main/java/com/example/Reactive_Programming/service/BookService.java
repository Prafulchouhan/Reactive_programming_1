package com.example.Reactive_Programming.service;

import com.example.Reactive_Programming.exception.BookNotFoundException;
import com.example.Reactive_Programming.model.Book;
import com.example.Reactive_Programming.model.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookInfoService bookInfoService;
    private final ReviewService reviewService;

    public Flux<Book> getBooks() {
        var allBook = bookInfoService.getBooks();
        return allBook.flatMap(bookInfo -> {
                            Mono<List<Review>> reviews =
                                    reviewService.getReview(bookInfo.getId()).collectList();
                            return reviews
                                    .map(review -> new Book(bookInfo, review));
                        }
                )
                .onErrorMap(throwable -> {
                            log.error("Exception is:" +throwable);
                            return new BookNotFoundException("Exception occurred while fetching book!!");
                        }
                ).log();
    }


    public Flux<Book> getBooksRetry() {
        var allBook = bookInfoService.getBooks();
        return allBook.flatMap(bookInfo -> {
                            Mono<List<Review>> reviews =
                                    reviewService.getReview(bookInfo.getId()).collectList();
                            return reviews
                                    .map(review -> new Book(bookInfo, review));
                        }
                )
                .onErrorMap(throwable -> {
                            log.error("Exception is:" +throwable);
                            return new BookNotFoundException("Exception occurred while fetching book!!");
                        }
                )
                .retry(3)
                .log();
    }

    public Flux<Book> getBooksRetryWhen() {
        var retrySpecs= getRetrySpecs();
        var allBook = bookInfoService.getBooks();
        return allBook.flatMap(bookInfo -> {
                            Mono<List<Review>> reviews =
                                    reviewService.getReview(bookInfo.getId()).collectList();
                            return reviews
                                    .map(review -> new Book(bookInfo, review));
                        }
                )
                .onErrorMap(throwable -> {
                            log.error("Exception is:" +throwable);
                            return new BookNotFoundException("Exception occurred while fetching book!!");
                        }
                )
                .retryWhen(retrySpecs)
                .log();
    }

    private static RetryBackoffSpec getRetrySpecs() {
        return Retry.backoff(
                3, Duration.ofMillis(1000)
        ).onRetryExhaustedThrow(
                ((retryBackoffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure())
                )
        );
    }

    public Mono<Book> getBookById(Integer id) {
        var book = bookInfoService.getBookById(id);
        var review = reviewService.getReview(id).collectList();
        return book
                .zipWith(review, (b, r) -> new Book(b, r)).log();
    }
}
