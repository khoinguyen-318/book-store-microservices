package com.bookstore.coreapis.enumaration;


public enum OrderStatus {
    CREATED("CREATED"), // New create order
    APPROVED("APPROVED"), // Admin accept or pre-paid
    CANCELLED("CANCELLED"), // Admin reject or payment failed
    SHIPPING("SHIPPING"), // Is shipping
    COMPLETED("COMPLETED"); // When complete
    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
