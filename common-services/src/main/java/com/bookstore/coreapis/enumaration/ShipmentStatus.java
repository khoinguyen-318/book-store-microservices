package com.bookstore.coreapis.enumaration;

public enum ShipmentStatus {
    ORDERED("ORDERED"),
    CANCELED("CANCELED"),
    TRANSIT("TRANSIT"),
    DELIVERED("DELIVERED");
    private final String status;

    ShipmentStatus(String status) {
        this.status = status;
    }
}
