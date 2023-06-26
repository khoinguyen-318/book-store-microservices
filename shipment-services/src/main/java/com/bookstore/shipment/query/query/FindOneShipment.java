package com.bookstore.shipment.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindOneShipment {
    private String orderId;
}
