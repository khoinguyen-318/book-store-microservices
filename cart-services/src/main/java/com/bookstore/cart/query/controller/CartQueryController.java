package com.bookstore.cart.query.controller;

import com.bookstore.cart.entities.Cart;
import com.bookstore.cart.query.services.ICartQueryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartQueryController {
    private final ICartQueryServices services;

    @GetMapping("/{customerId}")
    public CompletableFuture<Cart> getAllItemInCart(@PathVariable String customerId){
        return this.services.getAllItemInCart(customerId);
    }
}
