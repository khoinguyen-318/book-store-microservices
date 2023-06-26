package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusCommand {
    @TargetAggregateIdentifier
    private String orderId;

    private OrderStatus status;
}
