package com.bookstore.shipment.command.services;

import java.util.concurrent.CompletableFuture;

public interface IShipServices {

    CompletableFuture<Object> handlerShipment(String orderId, String status);
}
