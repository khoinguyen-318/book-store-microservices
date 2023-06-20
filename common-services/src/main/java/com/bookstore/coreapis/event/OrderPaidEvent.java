package com.bookstore.coreapis.event;

import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderPaidEvent {
    private String paymentId;
    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentState state;
    private BigDecimal totalPrice;
}
