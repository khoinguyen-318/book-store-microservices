package com.bookstore.order.command.services;

import com.bookstore.coreapis.command.CreateOrderCommand;
import com.bookstore.coreapis.enumaration.OrderStatus;
import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.order.command.model.CartResponse;
import com.bookstore.order.command.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommandServices implements IOrderCommandServices{
    private final CommandGateway commandGateway;
    private final WebClient webClient;
    @Override
    public CompletableFuture<Object> createOrder(OrderDto order) {
        final CartResponse cartFromServices = getCartFromServices(order.customerId()).join();
        if (Objects.isNull(cartFromServices)){
            throw new RuntimeException("Cart is not present");
        }

        return this.commandGateway.send(new CreateOrderCommand(
                UUID.randomUUID().toString(),
                order.customerId(),
                order.address(),
                order.phone(),
                PaymentMethod.valueOf(order.paymentMethod().toUpperCase()),
                cartFromServices.getItems(),
                OrderStatus.CREATED,
                cartFromServices.getTotalPrice()
        ));
    }
    @Async
    CompletableFuture<CartResponse> getCartFromServices(String customerId) {
        return CompletableFuture.supplyAsync(() -> this.webClient.get()
                .uri("http://localhost:8888/api/v1/cart/{customerId}", customerId)
                .retrieve()
                .onStatus(HttpStatus::isError, err -> Mono.error(new RuntimeException("An error occurred!")))
                .bodyToMono(CartResponse.class)
                .onErrorResume(RuntimeException.class, err -> {
                    log.info("Cart not present!");
                    return Mono.empty();
                })
                .block()
        );
    }
}
