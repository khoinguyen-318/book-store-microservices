package com.bookstore.shipment.command.services;

import com.bookstore.coreapis.command.ShipApprovedCommand;
import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentServices implements IShipServices{
    private final CommandGateway commandGateway;

    @Override
    public CompletableFuture<Object> handlerShipment(String orderId) {
        return this.commandGateway.send(
                new ShipApprovedCommand(
                        orderId
                )
        );
    }
}
