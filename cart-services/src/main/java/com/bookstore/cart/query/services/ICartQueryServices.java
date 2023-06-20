package com.bookstore.cart.query.services;

import com.bookstore.cart.entities.Cart;

import java.util.concurrent.CompletableFuture;

public interface ICartQueryServices {
    CompletableFuture<Cart> getAllItemInCart(String customerId);
}
