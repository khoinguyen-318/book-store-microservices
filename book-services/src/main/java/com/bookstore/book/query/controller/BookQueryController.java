package com.bookstore.book.query.controller;

import com.bookstore.book.entities.Book;
import com.bookstore.book.query.services.IBookQueryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookQueryController {
    private final IBookQueryServices services;
    @GetMapping
    public CompletableFuture<List<Book>> getAllBook(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10") Integer size,
                                                    @RequestParam(name = "sort",required = false,defaultValue = "title") String sort){
        return this.services.getAllBook(page,size,sort);
    }
    @GetMapping("/category/{categoryId}")
    public CompletableFuture<List<Book>> getAllBookByCategoryId(@PathVariable String categoryId,
                                                          @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size",required = false,defaultValue = "10") Integer size,
                                                          @RequestParam(name = "sort",required = false,defaultValue = "title") String sort){
        return this.services.getAllBookByCategoryId(categoryId,page,size,sort);
    }
    @GetMapping("/publisher/{publisher}")
    public CompletableFuture<List<Book>> getAllBookByPublisher(@PathVariable String publisher,
                                                         @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                         @RequestParam(name = "size",required = false,defaultValue = "10") Integer size,
                                                         @RequestParam(name = "sort",required = false,defaultValue = "title") String sort){
        return this.services.getAllBookByPublisher(publisher,page,size,sort);
    }
    @GetMapping("/series/{series}")
    public CompletableFuture<List<Book>> getAllBookSeries(@PathVariable String series,
                                                         @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                         @RequestParam(name = "size",required = false,defaultValue = "10") Integer size,
                                                         @RequestParam(name = "sort",required = false,defaultValue = "title") String sort){
        return this.services.getAllBookSeries(series,page,size,sort);
    }
    @GetMapping("/book/{bookId}")
    public CompletableFuture<Book> getDetailBookById(@PathVariable String bookId){
        return this.services.getDetailBookById(bookId);
    }
}
