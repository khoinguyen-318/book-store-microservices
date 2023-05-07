package com.bookstore.book.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
public class DeleteBookByIdCommand {
    @TargetAggregateIdentifier
    private String id;
}
