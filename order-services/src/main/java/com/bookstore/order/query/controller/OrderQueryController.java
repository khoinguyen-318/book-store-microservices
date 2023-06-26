package com.bookstore.order.query.controller;

import com.bookstore.coreapis.query.OrderResponse;
import com.bookstore.order.query.services.IOrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderQueryController {
    private final IOrderQueryService queryService;

    @GetMapping
    public CompletableFuture<List<OrderResponse>> getAllOrder() throws ExecutionException, InterruptedException {
        return this.queryService.getAllOrder();
    }

    @GetMapping("/{orderId}")
    public CompletableFuture<OrderResponse> getDetailOrder(@PathVariable String orderId){
        return this.queryService.getDetailOrder(orderId);
    }

}
