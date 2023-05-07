package com.bookstore.book.command.aggregate;

import com.bookstore.book.command.command.*;
import com.bookstore.book.command.event.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Aggregate
@Getter
@AllArgsConstructor
@NoArgsConstructor // Required by Axon
public class BookAggregate {
    @AggregateIdentifier
    private String id;
    private String title;
    private String slug;
    private String description;
    private BigDecimal price;
    private Set<String> imagesUrl = new HashSet<>();;
    private String categoryId;
    private Set<String> author = new HashSet<>();;
    private String publisher;
    private String series;
    private Integer pageCount;
    private Instant publicationDate;
    private Instant createdAt;
    private Instant modifiedAt;
    private String modifiedBy;
    private String createdBy;
    private boolean activated;

    /*
        Handler command add book
     */
    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        AggregateLifecycle.apply(new BookCreatedEvent(
                command.getId(),
                command.getTitle(),
                command.getSlug(),
                command.getDescription(),
                command.getPrice(),
                command.getImagesUrl(),
                command.getCategoryId(),
                command.getAuthor(),
                command.getPublisher(),
                command.getSeries(),
                command.getPageCount(),
                command.getPublicationDate(),
                command.getCreatedAt(),
                command.getCreatedBy(),
                command.isActivated()
        ));
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.price = event.getPrice();
//        this.imagesUrl = new HashSet<>();
        this.imagesUrl.clear();
        this.imagesUrl.addAll(event.getImagesUrl());
        this.categoryId = event.getCategoryId();
//        this.author = new HashSet<>();
        this.author.clear();
        this.author.addAll(event.getAuthor());
        this.publisher = event.getPublisher();
        this.series = event.getSeries();
        this.pageCount = event.getPageCount();
        this.publicationDate = event.getPublicationDate();
        this.createdAt = event.getCreatedAt();
        this.createdBy = event.getCreatedBy();
        this.activated = event.isActivated();
    }

    @CommandHandler
    public void handle(UpdateBookCommand command) {
        AggregateLifecycle.apply(new BookUpdatedEvent(
                command.getId(),
                command.getTitle(),
                command.getSlug(),
                command.getDescription(),
                command.getPrice(),
                command.getImagesUrl(),
                command.getCategoryId(),
                command.getAuthor(),
                command.getPublisher(),
                command.getSeries(),
                command.getPageCount(),
                command.getPublicationDate(),
                command.getModifiedAt(),
                command.getModifiedBy(),
                command.isActivated()
        ));
    }
    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.slug = event.getSlug();
        this.description = event.getDescription();
        this.price = event.getPrice();
        this.imagesUrl.clear();
        this.imagesUrl.addAll(event.getImagesUrl());
        this.categoryId = event.getCategoryId();
        this.author.clear();
        this.author.addAll(event.getAuthor());
        this.publisher = event.getPublisher();
        this.series = event.getSeries();
        this.pageCount = event.getPageCount();
        this.publicationDate = event.getPublicationDate();
        this.modifiedAt = event.getModifiedAt();
        this.modifiedBy = event.getModifiedBy();
        this.activated = event.isActivated();
    }
    @CommandHandler
    public void handle(DeleteBookByIdCommand command){
        AggregateLifecycle.apply(new BookDeletedByIdEvent(command.getId()));
    }
    @EventSourcingHandler
    public void on(BookDeletedByIdEvent event){
        this.id = event.getId();
    }
}
