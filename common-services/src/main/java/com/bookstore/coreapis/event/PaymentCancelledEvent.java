package com.bookstore.coreapis.event;

import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PaymentCancelledEvent {
    private String paymentId;
    private String orderId;
    private PaymentState state;
}
