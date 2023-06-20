package com.bookstore.payment.command.command;

import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FailedCreatePaypalInvoiceCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private PaymentState state;
}