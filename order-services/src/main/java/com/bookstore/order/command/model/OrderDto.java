package com.bookstore.order.command.model;

import javax.validation.constraints.NotBlank;

public record OrderDto(
        @NotBlank
        String customerId,
        String paymentMethod,
        @NotBlank
        String address,
        @NotBlank
        String phone
) {
}
