package com.bookstore.order.query.model;

import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Payment {
    private String id;
    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentState state;
    private BigDecimal totalPrice;
}
