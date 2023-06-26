package com.bookstore.order.query.services;

import com.bookstore.coreapis.query.OrderResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface IOrderQueryService {
    CompletableFuture<List<OrderResponse>> getAllOrder() throws ExecutionException, InterruptedException;

    CompletableFuture<OrderResponse> getDetailOrder(String orderId);
}
