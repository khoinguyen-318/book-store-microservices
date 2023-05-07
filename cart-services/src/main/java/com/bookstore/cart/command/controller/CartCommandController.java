package com.bookstore.cart.command.controller;

import com.bookstore.cart.command.model.ItemDto;
import com.bookstore.cart.command.services.ICartCommandServices;
import com.bookstore.cart.entities.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartCommandController {
    private final ICartCommandServices services;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Cart> addToCart(@Valid @RequestBody ItemDto book) throws ExecutionException, InterruptedException {
        return this.services.addToCart(book);
    }
    @PutMapping("/{cartId}")
    public CompletableFuture<Cart> updateItemInCart(@PathVariable String cartId,@Valid @RequestBody ItemDto book){
        return this.services.updateItemInCart(cartId,book);
    }
    @DeleteMapping("/{cartId}")
    public CompletableFuture<Cart> removeAllItemInCart(@PathVariable String cartId){
        return this.services.removeAllItemInCart(cartId);
    }
    @DeleteMapping("/{cartId}/books/{bookId}")
    public CompletableFuture<Cart> removeItemInCart(@PathVariable String bookId, @PathVariable String cartId){
        return this.services.removeItemInCart(cartId,bookId);
    }
}
