package com.bookstore.book.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Document
@Builder
@Getter
@Setter
public class Book{
    @MongoId
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
    @Column(updatable = false)
    private Instant createdAt;
    @Column(insertable = false)
    private Instant modifiedAt;
    @Column(insertable = false)
    private String modifiedBy;
    @Column(updatable = false)
    private String createdBy;
    private boolean activated;
}
