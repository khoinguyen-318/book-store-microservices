package com.bookstore.shipment.command.controller;

import com.bookstore.shipment.command.services.IShipServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shipment")
public class ShipCommandController {
    private final IShipServices shipServices;

    @PatchMapping("/{orderId}")
    public CompletableFuture<Object> handlerShipment(@PathVariable String orderId){
        return this.shipServices.handlerShipment(orderId);
    }
}
