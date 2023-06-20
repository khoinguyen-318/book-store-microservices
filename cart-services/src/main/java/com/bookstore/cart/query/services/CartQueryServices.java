package com.bookstore.cart.query.services;

import com.bookstore.cart.entities.Cart;
import com.bookstore.cart.query.query.FindAllItemInCart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "carts")
public class CartQueryServices implements ICartQueryServices{
    private final QueryGateway queryGateway;

    @Override
    @Cacheable
    public CompletableFuture<Cart> getAllItemInCart(String customerId) {
        return this.queryGateway.query(new FindAllItemInCart(customerId),
                ResponseTypes.instanceOf(Cart.class));
    }
}
