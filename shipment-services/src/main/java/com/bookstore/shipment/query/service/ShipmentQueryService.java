package com.bookstore.shipment.query.service;

import com.bookstore.shipment.entities.Shipment;
import com.bookstore.shipment.query.query.FindAllShipment;
import com.bookstore.shipment.query.query.FindOneShipment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentQueryService implements IShipmentQueryService{

    private final QueryGateway queryGateway;

    @Override
    public CompletableFuture<List<Shipment>> getAllShipment() {
        return this.queryGateway.query(new FindAllShipment(),
                ResponseTypes.multipleInstancesOf(Shipment.class));
    }

    @Override
    public CompletableFuture<Shipment> getShipment(String orderId) {
        return this.queryGateway.query(new FindOneShipment(orderId),
                ResponseTypes.instanceOf(Shipment.class));
    }
}
