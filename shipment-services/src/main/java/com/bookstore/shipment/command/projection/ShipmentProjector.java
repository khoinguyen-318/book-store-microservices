package com.bookstore.shipment.command.projection;

import com.bookstore.coreapis.event.ShipOrderedEvent;
import com.bookstore.shipment.entities.Shipment;
import com.bookstore.shipment.entities.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentProjector {
    private final ShipmentRepository shipmentRepository;

    @EventHandler
    public void on(ShipOrderedEvent event){
        Shipment shipment = Shipment.builder()
                .id(event.getShipId())
                .orderId(event.getOrderId())
                .status(event.getState())
                .build();
        this.shipmentRepository.save(shipment);
    }
}
