package com.bookstore.categories.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class DeleteCategoryCommand {
    @TargetAggregateIdentifier
    private String id;
}
