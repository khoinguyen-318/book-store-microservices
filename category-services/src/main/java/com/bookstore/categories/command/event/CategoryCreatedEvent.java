package com.bookstore.categories.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class CategoryCreatedEvent {
    private String id;
    private String name;
    private String slug;
    private Instant createdAt;
    private Instant modifiedAt;
    private String modifiedBy;
    private String createdBy;
    private boolean activated;
}
