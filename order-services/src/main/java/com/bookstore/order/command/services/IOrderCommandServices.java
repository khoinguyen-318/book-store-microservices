package com.bookstore.order.command.services;

import com.bookstore.order.command.model.OrderDto;

import java.util.concurrent.CompletableFuture;

public interface IOrderCommandServices {
    CompletableFuture<Object> createOrder(OrderDto order);
}
