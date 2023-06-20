package com.bookstore.coreapis.event;

import com.bookstore.coreapis.enumaration.OrderStatus;
import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private String address;
    private String phone;
    private PaymentMethod paymentMethod;
    private Set<Item> items;

    private OrderStatus status;
    private Date orderedDate;
    private BigDecimal totalPrice;

    public OrderCreatedEvent() {
        this.orderedDate = new Date();
    }

    public OrderCreatedEvent(String orderId, String customerId, String address, String phone, PaymentMethod paymentMethod, Set<Item> items, OrderStatus status, BigDecimal totalPrice) {
        this();
        this.orderId = orderId;
        this.customerId = customerId;
        this.address = address;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
    }
}
