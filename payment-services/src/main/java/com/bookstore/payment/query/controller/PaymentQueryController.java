package com.bookstore.payment.query.controller;

import com.bookstore.payment.entities.Payment;
import com.bookstore.payment.query.services.IPaymentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentQueryController {
    private final IPaymentQueryService queryService;

    @GetMapping
    public CompletableFuture<List<Payment>> getAllPayment(){
        return this.queryService.getAllPayment();
    }
    @GetMapping("/{orderId}")
    public CompletableFuture<Payment> getPayment(@PathVariable String orderId){
        return this.queryService.getPayment(orderId);
    }

}
