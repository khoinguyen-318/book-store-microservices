package com.bookstore.cart.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookInCartIncreasedEvent {
    private String cartId;
    private String bookId;
    private Integer quantity;
    private BigDecimal price;

}
