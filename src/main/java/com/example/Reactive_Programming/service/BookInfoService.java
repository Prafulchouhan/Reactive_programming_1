package com.example.Reactive_Programming.service;

import com.example.Reactive_Programming.model.BookInfo;
import com.example.Reactive_Programming.repo.BookInfoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookInfoService {
    @Autowired
    private BookInfoRepo repo;

    public Mono<BookInfo> createBook(BookInfo book){
        return repo.save(book);
    }

    public Flux<BookInfo> getBooks(){
        return repo.findAll();
    }

    public Mono<BookInfo> getBookById(Integer id){
        var book=new BookInfo(id,"Book One","Author One","121212");
//        return Mono.just(book);
        return repo.findById(id);
    }

    public Mono<Void> deleteBookById(Integer id){
        return repo.deleteById(id);
    }

    public Mono<BookInfo> updateBook(BookInfo bookInfo,Integer id){
        return repo.findById(id)
                .map(book->{
                    book.setTitle( bookInfo.getTitle()!=null ? bookInfo.getTitle() : book.getTitle());
                    book.setAuthor(bookInfo.getAuthor()!=null?bookInfo.getAuthor():book.getAuthor());
                    book.setISBN(book.getISBN()!=null? book.getISBN() : book.getISBN());
                    return book;
                }).flatMap(data->repo.save(data));
    }

}
