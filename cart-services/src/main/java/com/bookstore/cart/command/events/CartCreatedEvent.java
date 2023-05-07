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
public class CartCreatedEvent {
    private String cartId;
    private String customerId;
    private BigDecimal totalPrice;

}
