package com.bookstore.cart.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartCommand {
    @TargetAggregateIdentifier
    private String cartId;
    private String customerId;
    private BigDecimal totalPrice;
}
