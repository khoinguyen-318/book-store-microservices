package com.bookstore.order.query.services;

import com.bookstore.coreapis.query.OrderResponse;
import com.bookstore.order.entities.Order;
import com.bookstore.order.query.model.Payment;
import com.bookstore.order.query.model.Shipment;
import com.bookstore.order.query.query.FindAllOrder;
import com.bookstore.order.query.query.FindOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderQueryService implements IOrderQueryService{
    private final QueryGateway queryGateway;
    private final WebClient webClient;
    @Override
    public CompletableFuture<List<OrderResponse>> getAllOrder() throws ExecutionException, InterruptedException {

        // Get all order from Order service
        final List<Order> orders = this.queryGateway.query(new FindAllOrder(),
                ResponseTypes.multipleInstancesOf(Order.class)).get();
        // Get all payment from payment service
        final List<Payment> payments = this.webClient.get()
                .uri("http://localhost:8888/api/v1/payment/")
                .retrieve()
                .onStatus(HttpStatus::isError,
                        err -> Mono.error(new RuntimeException("An error!")))
                .bodyToFlux(Payment.class)
                .collectList()
                .block();
        // Get all shipment from shipment service
        final List<Shipment> shipments = this.webClient.get()
                .uri("http://localhost:8888/api/v1/shipment/")
                .retrieve()
                .onStatus(HttpStatus::isError,
                        err -> Mono.error(new RuntimeException("An error!")))
                .bodyToFlux(Shipment.class)
                .collectList()
                .block();
        final List<OrderResponse> orderResponses = orders.stream()
                .flatMap(order -> payments.stream()
                        .filter(payment -> order.getId().equals(payment.getOrderId()))
                        .flatMap(payment -> shipments.stream()
                                .filter(shipment -> shipment.getOrderId().equals(payment.getOrderId()))
                                .map(shipment -> OrderResponse.builder()
                                        .orderId(order.getId())
                                        .customer(order.getCustomerId())
                                        .address(order.getAddress())
                                        .phone(order.getPhone())
                                        .paymentMethod(String.valueOf(payment.getPaymentMethod()))
                                        .totalPrice(payment.getTotalPrice())
                                        .items(Collections.singleton(order.getOrderLines()))
                                        .orderDate(order.getOrderedDate())
                                        .status(Map.of("order",String.valueOf(order.getStatus()),
                                                        "payment",String.valueOf(payment.getState()),
                                                        "shipment",String.valueOf(shipment.getStatus())))
                                        .build()
                                )))
                .toList();
        return CompletableFuture.completedFuture(orderResponses);
    }

    @Override
    public CompletableFuture<OrderResponse> getDetailOrder(String orderId) {
        return this.queryGateway.query(new FindOrder(orderId),
                ResponseTypes.instanceOf(OrderResponse.class));
    }
}
