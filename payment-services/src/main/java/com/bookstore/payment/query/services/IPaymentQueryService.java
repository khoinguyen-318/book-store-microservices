package com.bookstore.payment.query.services;

import com.bookstore.payment.entities.Payment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IPaymentQueryService {
    CompletableFuture<Payment> getPayment(String orderId);

    CompletableFuture<List<Payment>> getAllPayment();
}
