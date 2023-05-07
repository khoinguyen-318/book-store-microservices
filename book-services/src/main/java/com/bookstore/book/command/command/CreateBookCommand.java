package com.bookstore.book.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CreateBookCommand {
    @TargetAggregateIdentifier
    private String id;
    private String title;
    private String slug;
    private String description;
    private BigDecimal price;
    private Set<String> imagesUrl;
    private String categoryId;
    private Set<String> author;
    private String publisher;
    private String series;
    private Integer pageCount;
    private Instant publicationDate;
    private Instant createdAt;
    private String createdBy;
    private boolean activated;
}
