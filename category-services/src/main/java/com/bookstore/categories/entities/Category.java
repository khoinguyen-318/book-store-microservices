package com.bookstore.categories.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Column;
import java.time.Instant;

@Document
@Getter
@Setter
@Builder
public class Category{
    @MongoId
    private String id;
    private String name;
    private String slug;
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
