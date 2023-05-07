package com.bookstore.cart.command.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class BookAddedToCartEvent {
    private String cartId;
    private String customerId;
    private String bookId;
    private Integer quantity;

    private BigDecimal price;
    private BigDecimal totalPrice;

    public BookAddedToCartEvent(String cartId, String customerId, String bookId, Integer quantity, BigDecimal price) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }
}
