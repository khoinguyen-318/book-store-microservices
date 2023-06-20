package com.bookstore.coreapis.event;

import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApprovedShipmentEvent {
    private String shipId;
    private String orderId;
    private ShipmentStatus state;
}
