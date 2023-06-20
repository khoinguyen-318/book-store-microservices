package com.bookstore.order.command.model;

import com.bookstore.coreapis.model.Item;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
public class CartResponse {
    private String id;
    private String customerId;
    private Set<Item> items;
    private BigDecimal totalPrice;

}
