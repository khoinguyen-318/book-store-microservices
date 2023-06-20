package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateInvoiceCommand {
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentMethod paymentMethod;
    private BigDecimal totalPrice;
}
