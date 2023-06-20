package com.bookstore.payment.entities;

import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Payment {
    @Id
    private String id;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private PaymentState state;
    private BigDecimal totalPrice;
}
