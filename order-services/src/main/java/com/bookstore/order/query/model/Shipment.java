package com.bookstore.order.query.model;

import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shipment {
    private String id;
    private String orderId;
    private ShipmentStatus status;
}
