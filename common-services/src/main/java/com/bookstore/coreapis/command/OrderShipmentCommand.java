package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderShipmentCommand {
    @TargetAggregateIdentifier
    private String shipId;
    private String orderId;
    private final ShipmentStatus state = ShipmentStatus.ORDERED;
}
