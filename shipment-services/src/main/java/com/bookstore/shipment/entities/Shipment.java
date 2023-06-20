package com.bookstore.shipment.entities;

import com.bookstore.coreapis.enumaration.ShipmentStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shipment {
    @Id
    private String id;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}
