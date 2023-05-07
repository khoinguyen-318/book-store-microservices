package com.bookstore.cart.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Document
@Builder
public class Cart {
    @MongoId
    private String id;
    @Indexed(unique = true)
    private String customerId;
    private Set<Item> items;
    private BigDecimal totalPrice;
}
