package com.bookstore.payment.command.services;

import com.bookstore.payment.command.model.OrderDTO;
import lombok.SneakyThrows;

public interface IPaymentServices {
    @SneakyThrows
    Object createPayment(String orderId, OrderDTO orderDTO);


    Object capturePayment(String orderId, String token);

    Object cancelPayment(String orderId);

    Object updateStatus(String orderId, String status);
}
