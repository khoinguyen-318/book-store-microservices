package com.bookstore.order.command.services;

import com.bookstore.order.command.model.OrderDto;
import com.bookstore.order.entities.Order;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IOrderCommandServices {
    CompletableFuture<Object> createOrder(OrderDto order) throws ExecutionException, InterruptedException;

    CompletableFuture<Order> updateStatus(String orderId, String status);
}
