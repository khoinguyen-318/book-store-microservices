package com.bookstore.cart.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOneBookInCartCommand {
    @TargetAggregateIdentifier
    private String cartId;
    private String bookId;
}
