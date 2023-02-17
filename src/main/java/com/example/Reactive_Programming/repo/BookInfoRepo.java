package com.example.Reactive_Programming.repo;

import com.example.Reactive_Programming.model.BookInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookInfoRepo extends ReactiveCrudRepository<BookInfo,Integer> {
}
