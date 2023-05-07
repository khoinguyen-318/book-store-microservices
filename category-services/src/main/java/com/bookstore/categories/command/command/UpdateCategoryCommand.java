package com.bookstore.categories.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCategoryCommand {
    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String slug;
    private Instant createdAt;
    private Instant modifiedAt;
    private String modifiedBy;
    private String createdBy;
    private boolean activated;
}
