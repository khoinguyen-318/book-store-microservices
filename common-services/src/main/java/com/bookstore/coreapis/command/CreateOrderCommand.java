package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.OrderStatus;
import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String customerId;
    private String address;
    private String phone;
    private PaymentMethod paymentMethod;
    private Set<Item> items;
    private OrderStatus status;
    private BigDecimal totalPrice;
}
