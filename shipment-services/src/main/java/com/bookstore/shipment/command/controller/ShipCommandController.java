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
    public CompletableFuture<Object> updateStatusShipment(@PathVariable String orderId,
                                                     @RequestBody String status){
        return this.shipServices.handlerShipment(orderId,status);
    }


}
