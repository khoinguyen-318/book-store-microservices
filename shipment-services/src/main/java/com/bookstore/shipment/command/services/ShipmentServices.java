package com.bookstore.shipment.command.services;

import com.bookstore.coreapis.command.UpdateShipmentCommand;
import com.bookstore.coreapis.enumaration.ShipmentStatus;
import com.bookstore.shipment.entities.Shipment;
import com.bookstore.shipment.entities.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentServices implements IShipServices{
    private final CommandGateway commandGateway;

    private final ShipmentRepository shipmentRepository;

    @Override
    public CompletableFuture<Object> handlerShipment(String orderId, String status) {
        final Optional<Shipment> shipment = this.shipmentRepository.findByOrderId(orderId);
        if (shipment.isPresent()){
            return this.commandGateway.send(
                    new UpdateShipmentCommand(
                            shipment.get().getId(),
                            orderId,
                            ShipmentStatus.valueOf(status.toUpperCase())
                    )
            );
        }

        return CompletableFuture.completedFuture("Not correct order id");
    }
}
