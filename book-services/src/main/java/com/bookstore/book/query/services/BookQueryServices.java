package com.bookstore.book.query.services;

import com.bookstore.book.entities.Book;
import com.bookstore.book.query.query.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookQueryServices implements IBookQueryServices{
    private final QueryGateway queryGateway;
    @Override
    public CompletableFuture<List<Book>> getAllBook(Integer page, Integer size, String sort) {
        log.info("Get info from database");
        return this.queryGateway.query(new FindAllBook(page,size,sort),
                ResponseTypes.multipleInstancesOf(Book.class));
    }

    @Override
    public CompletableFuture<Book> getDetailBookById(String bookId) {
        log.info("Get info from database with id = [{}]",bookId);
        return this.queryGateway.query(new FindBookById(bookId),
                ResponseTypes.instanceOf(Book.class));
    }

    @Override
    public CompletableFuture<List<Book>> getAllBookByCategoryId(String categoryId, Integer page, Integer size, String sort) {
        log.info("Get info from database");
        return this.queryGateway.query(new FindAllBookByCategory(categoryId,page,size,sort),
                ResponseTypes.multipleInstancesOf(Book.class));
    }

    @Override
    public CompletableFuture<List<Book>> getAllBookByPublisher(String publisher, Integer page, Integer size, String sort) {
        log.info("Get info from database");
        return this.queryGateway.query(new FindAllBookByPublisher(publisher,page,size,sort),
                ResponseTypes.multipleInstancesOf(Book.class));
    }

    @Override
    public CompletableFuture<List<Book>> getAllBookSeries(String series, Integer page, Integer size, String sort) {
        log.info("Get info from database");
        return this.queryGateway.query(new FindAllBookBySeries(series,page,size,sort),
                ResponseTypes.multipleInstancesOf(Book.class));
    }
}
