package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private final OrderStatus orderStatus = OrderStatus.CANCELLED;
}
