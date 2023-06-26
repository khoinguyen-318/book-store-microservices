package com.bookstore.coreapis.event;

import com.bookstore.coreapis.enumaration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderStatusUpdated {
    private String orderId;
    private OrderStatus status;
}
