package com.bookstore.book.command.controller;

import com.bookstore.book.command.services.IBookCommandServices;
import com.bookstore.book.entities.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookCommandController {
    private final IBookCommandServices services;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Book> createBook(@RequestParam("book") String book,
                                              @RequestParam("files") MultipartFile[] files) throws JsonProcessingException, ExecutionException, InterruptedException {
        return this.services.createBook(book,files);
    }
    @PutMapping("/{bookId}")
    public CompletableFuture<Book> updateBook(@RequestParam("book") String book,
                                              @RequestParam("files") MultipartFile[] files,
                                              @PathVariable String bookId) throws JsonProcessingException, ExecutionException, InterruptedException {
        return this.services.updateBook(bookId,book,files);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> deleteBookById(@PathVariable String bookId){
        return this.services.deleteBookById(bookId);
    }
}
