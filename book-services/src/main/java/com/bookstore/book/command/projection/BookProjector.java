package com.bookstore.book.command.projection;

import com.bookstore.book.command.event.BookCreatedEvent;
import com.bookstore.book.command.event.BookDeletedByIdEvent;
import com.bookstore.book.command.event.BookUpdatedEvent;
import com.bookstore.book.entities.Book;
import com.bookstore.book.query.query.*;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookProjector {
    private final MongoTemplate mongoTemplate;

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = Book.builder()
                .id(event.getId())
                .title(event.getTitle())
                .slug(event.getSlug())
                .description(event.getDescription())
                .price(event.getPrice())
                .imagesUrl(event.getImagesUrl())
                .categoryId(event.getCategoryId())
                .author(event.getAuthor())
                .publisher(event.getPublisher())
                .series(event.getSeries())
                .pageCount(event.getPageCount())
                .publicationDate(event.getPublicationDate())
                .createdAt(event.getCreatedAt())
                .createdBy(event.getCreatedBy())
                .activated(event.isActivated())
                .build();
        this.mongoTemplate.save(book);
    }
    @EventHandler
    public void on(BookUpdatedEvent event){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(event.getId()));
        Book book = Book.builder()
                .id(event.getId())
                .title(event.getTitle())
                .slug(event.getSlug())
                .description(event.getDescription())
                .price(event.getPrice())
                .imagesUrl(event.getImagesUrl())
                .categoryId(event.getCategoryId())
                .author(event.getAuthor())
                .publisher(event.getPublisher())
                .series(event.getSeries())
                .pageCount(event.getPageCount())
                .publicationDate(event.getPublicationDate())
                .modifiedAt(event.getModifiedAt())
                .modifiedBy(event.getModifiedBy())
                .activated(event.isActivated())
                .build();
        Document object = new Document();
        mongoTemplate.getConverter().write(book, object);
        Update update = Update.fromDocument(object);
        this.mongoTemplate.updateFirst(query, update,Book.class);
    }
    @EventHandler
    public void on(BookDeletedByIdEvent event){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(event.getId()));
        mongoTemplate.remove(query,Book.class);
    }
    @QueryHandler
    public List<Book> handle(FindAllBook book){
        Query query = new Query();
        Pageable pageable = PageRequest.of(book.getPage(), book.getSize(), Sort.by(book.getSort()));
        query.with(pageable);
        return PageableExecutionUtils
                .getPage(
                    mongoTemplate.find(query,Book.class),
                    pageable,
                    ()->mongoTemplate.count(query.limit(0).skip(0),Book.class))
                .toList();
    }
    @QueryHandler
    public Book handle(FindBookById book){
        return mongoTemplate.findById(book.getBookId(),Book.class);
    }
    @QueryHandler
    public List<Book> handle(FindAllBookByCategory book){
        Query query = new Query();
        Pageable pageable = PageRequest.of(book.getPage(), book.getSize(), Sort.by(book.getSort()));
        query.with(pageable);
        query.addCriteria(Criteria.where("categoryId").is(book.getCategoryId()));
        return PageableExecutionUtils
                .getPage(
                        mongoTemplate.find(query,Book.class),
                        pageable,
                        ()->mongoTemplate.count(query.limit(0).skip(0),Book.class))
                .toList();
    }
    @QueryHandler
    public List<Book> handle(FindAllBookByPublisher book){
        Query query = new Query();
        Pageable pageable = PageRequest.of(book.getPage(), book.getSize(), Sort.by(book.getSort()));
        query.with(pageable);
        query.addCriteria(Criteria.where("publisher").is(book.getPublisher()));
        return PageableExecutionUtils
                .getPage(
                        mongoTemplate.find(query,Book.class),
                        pageable,
                        ()->mongoTemplate.count(query.limit(0).skip(0),Book.class))
                .toList();
    }
    @QueryHandler
    public List<Book> handle(FindAllBookBySeries book){
        Query query = new Query();
        Pageable pageable = PageRequest.of(book.getPage(), book.getSize(), Sort.by(book.getSort()));
        query.with(pageable);
        query.addCriteria(Criteria.where("series").is(book.getSeries()));
        return PageableExecutionUtils
                .getPage(
                        mongoTemplate.find(query,Book.class),
                        pageable,
                        ()->mongoTemplate.count(query.limit(0).skip(0),Book.class))
                .toList();
    }
}
