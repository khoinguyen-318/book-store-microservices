package com.bookstore.shipment.command.aggregate;

import com.bookstore.coreapis.command.OrderShipmentCommand;
import com.bookstore.coreapis.enumaration.ShipmentStatus;
import com.bookstore.coreapis.event.ShipOrderedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Setter
@Getter
@NoArgsConstructor //required by axon
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipId;
    private String orderId;
    private ShipmentStatus state;

    @CommandHandler
    public ShipmentAggregate(OrderShipmentCommand command){
        AggregateLifecycle.apply(new ShipOrderedEvent(
                command.getShipId(),
                command.getOrderId(),
                command.getState()
        ));
    }
    @EventSourcingHandler
    public void on(ShipOrderedEvent event){
        this.shipId = event.getShipId();
        this.orderId = event.getOrderId();
        this.state = event.getState();
    }
}
