package com.bookstore.book.command.services;

import com.bookstore.book.command.command.CreateBookCommand;
import com.bookstore.book.command.command.DeleteBookByIdCommand;
import com.bookstore.book.command.command.UpdateBookCommand;
import com.bookstore.book.command.model.BookDto;
import com.bookstore.book.command.model.CategoryResponse;
import com.bookstore.book.entities.Book;
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookCommandServices implements IBookCommandServices {
    private final CommandGateway commandGateway;
    private final WebClient webClient;
    private final Cloudinary cloudinary = Singleton.getCloudinary();
    private final ObjectMapper objectMapper;
    private final MongoTemplate template;
    @Override
    public CompletableFuture<Book> createBook(String data, MultipartFile[] files) throws ExecutionException, InterruptedException {

        BookDto book = this.parseStringJsonToObject(data).get();

        if (!this.getCategoryFromCategoryServices(book.categoryId()).get()){
            throw new RuntimeException("Category is not present");
        }

        Set<String> imagesUrl = this.uploadImagesToCloudinary(files).get();

        return this.commandGateway.send(new CreateBookCommand(
                UUID.randomUUID().toString(),
                book.title(),
                book.slug(),
                book.description(),
                book.price(),
                imagesUrl,
                book.categoryId(),
                book.author(),
                book.publisher(),
                book.series(),
                book.pageCount(),
                book.publicationDate(),
                Instant.now(),
                "Admin",
                book.activated()
        ));
    }

    @Override
    public CompletableFuture<Void> deleteBookById(String bookId) {
        notNull(bookId,"Book need to deleted not null");
        log.info("Delete book id = [{}]",bookId);
        final Book bookExist = template.findById(bookId, Book.class);
        if (bookExist == null){
            throw new RuntimeException("Book id = "+bookId+" is not exist");
        }
        return this.commandGateway.send(new DeleteBookByIdCommand(bookId));
    }

    @Override
    public CompletableFuture<Book> updateBook(String bookId, String data, MultipartFile[] files) throws ExecutionException, InterruptedException {
        notNull(bookId,"Book need to update not null");
        log.info("Update book id = [{}]",bookId);
        final Book bookExist = template.findById(bookId, Book.class);
        if (bookExist == null){
            throw new RuntimeException("Book id = "+bookId+" is not exist");
        }
        BookDto book = this.parseStringJsonToObject(data).get();

        this.getCategoryFromCategoryServices(book.categoryId());

        Set<String> imagesUrl = this.uploadImagesToCloudinary(files).get();

        return this.commandGateway.send(new UpdateBookCommand(
                bookId,
                book.title(),
                book.slug(),
                book.description(),
                book.price(),
                imagesUrl,
                book.categoryId(),
                book.author(),
                book.publisher(),
                book.series(),
                book.pageCount(),
                book.publicationDate(),
                Instant.now(),
                "Admin",
                book.activated()
        ));
    }

    @Async
    CompletableFuture<Boolean> getCategoryFromCategoryServices(String categoryId){
        log.info("Thread- {} -doing get category!",Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
            final CategoryResponse categoryIsPresent = this.webClient.get()
                    .uri("http://localhost:8888/api/v1/category/{categoryId}", categoryId)
                    .retrieve()
                    .onStatus(HttpStatus::isError,
                            err -> Mono.error(new RuntimeException("An error!")))
                    .bodyToMono(CategoryResponse.class)
                    .onErrorResume(RuntimeException.class, err -> {
                        log.info("Category not present!");
                        return Mono.empty();
                    })
                    .block();
            return categoryIsPresent != null;
        });
    }
    @Async
    CompletableFuture<Set<String>> uploadImagesToCloudinary(MultipartFile[] files){
        log.info("Thread- {} -uploading images to cloudinary!",Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(()->{
            Set<String> imagesUrl = new HashSet<>();
            if (!(files.length == 0)) {
                Arrays.stream(files).forEach(image -> {
                    try {
                        final Map upload = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
                        // Get image url with key url or secure_url
                        imagesUrl.add(upload.get("url").toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return imagesUrl;
        });
    }
    @Async
    public CompletableFuture<BookDto> parseStringJsonToObject(String value){
        log.info("Thread- {} -convert string Json!",Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(()->{
            try {
                return this.objectMapper.readValue(value, BookDto.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
