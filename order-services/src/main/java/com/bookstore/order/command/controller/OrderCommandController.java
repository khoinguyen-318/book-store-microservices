package com.bookstore.order.command.controller;

import com.bookstore.order.command.model.OrderDto;
import com.bookstore.order.command.services.IOrderCommandServices;
import com.bookstore.order.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderCommandController {
    private final IOrderCommandServices services;

    @PostMapping
    public CompletableFuture<Object> createOrder(@RequestBody OrderDto order){
        return this.services.createOrder(order);
    }
//    @PatchMapping("/{orderId}")
//    public CompletableFuture<Order> updateStatus(@PathVariable String orderId,){
//
//    }
}
