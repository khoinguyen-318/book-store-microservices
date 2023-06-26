package com.bookstore.payment.query.services;

import com.bookstore.payment.entities.Payment;
import com.bookstore.payment.query.query.FindAllPayment;
import com.bookstore.payment.query.query.FindOnePayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentQueryService implements IPaymentQueryService{

    private final QueryGateway queryGateway;
    @Override
    public CompletableFuture<Payment> getPayment(String orderId) {
        return this.queryGateway.query(new FindOnePayment(orderId),
                ResponseTypes.instanceOf(Payment.class));
    }

    @Override
    public CompletableFuture<List<Payment>> getAllPayment() {
        return this.queryGateway.query(new FindAllPayment(),
                ResponseTypes.multipleInstancesOf(Payment.class));
    }
}
