
package com.example.Reactive_Programming.service;

        import com.example.Reactive_Programming.model.Review;
        import com.example.Reactive_Programming.repo.ReviewRepo;
        import lombok.RequiredArgsConstructor;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;

        import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo repo;

    public Flux<Review> getReview(Integer bookId){

//        var review= List.of(
//                new Review(1,bookId,2.0,"comment1"),
//                new Review(2,bookId,4.0,"comment2")
//        );
//        return Flux.fromIterable(review);
        return repo.findReviewByBookId(bookId);
    }

    public Mono<Review> createReview(Review review){
        return repo.save(review);
    }

    public Mono<Void> deleteReview(Integer id){
        return repo.deleteById(id);
    }

}
/*
@Service
public class ReviewService {

    @Autowired
    private ReviewRepo repo;

    public List<Review> getReview(Integer bookId){
        return repo.findReviewByBookId(bookId);
    }

    public Review createReview(Review review){
        return repo.save(review);
    }

    public Void deleteReview(Integer id){
        return repo.deleteById(id);
    }

}
 */
