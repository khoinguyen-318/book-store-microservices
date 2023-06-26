package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentCommand {
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
    private ShipmentStatus status;
}
