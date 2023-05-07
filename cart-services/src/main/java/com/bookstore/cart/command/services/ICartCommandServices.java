package com.bookstore.cart.command.services;

import com.bookstore.cart.command.model.ItemDto;
import com.bookstore.cart.entities.Cart;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ICartCommandServices {
    CompletableFuture<Cart> addToCart(ItemDto book) throws ExecutionException, InterruptedException;

    CompletableFuture<Cart> removeAllItemInCart(String cartId);

    CompletableFuture<Cart> removeItemInCart(String cartId, String bookId);

    CompletableFuture<Cart> updateItemInCart(String cartId,ItemDto book);
}
