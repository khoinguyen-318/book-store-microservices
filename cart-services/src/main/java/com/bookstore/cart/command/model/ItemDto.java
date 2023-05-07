package com.bookstore.cart.command.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record ItemDto(
        @NotBlank
        String customerId,
        @NotBlank
        String bookId,
        @Min(0)
        Integer quantity
) {
}
