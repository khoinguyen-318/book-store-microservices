package com.bookstore.shipment.command.projection;

import com.bookstore.coreapis.event.ShipOrderedEvent;
import com.bookstore.coreapis.event.ShipmentStatusUpdatedEvent;
import com.bookstore.shipment.entities.Shipment;
import com.bookstore.shipment.entities.ShipmentRepository;
import com.bookstore.shipment.query.query.FindAllShipment;
import com.bookstore.shipment.query.query.FindOneShipment;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    @EventHandler
    public void on(ShipmentStatusUpdatedEvent event){
        final Optional<Shipment> shipment = this.shipmentRepository.findByOrderId(event.getOrderId());
        shipment.ifPresent((s)->{
            s.setStatus(event.getState());
            this.shipmentRepository.save(s);
        });
    }
    // handle for read
    @QueryHandler
    public List<Shipment> getAllShipment(FindAllShipment query){
        return this.shipmentRepository.findAll();
    }

    @QueryHandler
    public Shipment getShipment(FindOneShipment query){
        return this.shipmentRepository.findByOrderId(query.getOrderId()).orElseGet(null);
    }
}
