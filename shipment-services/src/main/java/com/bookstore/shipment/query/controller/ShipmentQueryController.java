package com.bookstore.shipment.query.controller;

import com.bookstore.shipment.entities.Shipment;
import com.bookstore.shipment.query.service.IShipmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/shipment")
public class ShipmentQueryController {
    private final IShipmentQueryService queryService;

    @GetMapping
    public CompletableFuture<List<Shipment>> getAllShipment(){
        return this.queryService.getAllShipment();
    }
    @GetMapping("/{orderId}")
    public CompletableFuture<Shipment> getShipment(@PathVariable String orderId){
        return this.queryService.getShipment(orderId);
    }
}
