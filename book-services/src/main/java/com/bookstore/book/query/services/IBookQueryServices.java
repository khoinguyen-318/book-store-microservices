package com.bookstore.book.query.services;

import com.bookstore.book.entities.Book;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IBookQueryServices {
    CompletableFuture<List<Book>> getAllBook(Integer page, Integer size, String sort);


    CompletableFuture<Book> getDetailBookById(String bookId);

    CompletableFuture<List<Book>> getAllBookByCategoryId(String categoryId, Integer page, Integer size, String sort);

    CompletableFuture<List<Book>> getAllBookByPublisher(String publisher, Integer page, Integer size, String sort);

    CompletableFuture<List<Book>> getAllBookSeries(String series, Integer page, Integer size, String sort);
}
