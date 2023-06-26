package com.bookstore.shipment.query.service;

import com.bookstore.shipment.entities.Shipment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IShipmentQueryService {
    CompletableFuture<List<Shipment>> getAllShipment();

    CompletableFuture<Shipment> getShipment(String orderId);
}
