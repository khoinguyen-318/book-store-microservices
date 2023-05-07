package com.bookstore.cart.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInCartUpdatedEvent {
    private String cartId;
    private String customerId;
    private String bookId;
    private Integer quantity;
}
