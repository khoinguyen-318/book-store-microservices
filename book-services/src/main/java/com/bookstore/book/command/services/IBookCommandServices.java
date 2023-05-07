package com.bookstore.book.command.services;

import com.bookstore.book.command.model.BookDto;
import com.bookstore.book.entities.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IBookCommandServices {
    CompletableFuture<Book> createBook(String book, MultipartFile[] files) throws JsonProcessingException, ExecutionException, InterruptedException;

    CompletableFuture<Void> deleteBookById(String bookId);

    CompletableFuture<Book> updateBook(String bookId, String book, MultipartFile[] files) throws ExecutionException, InterruptedException;
}
