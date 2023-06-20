package com.bookstore.coreapis.command;

import com.bookstore.coreapis.enumaration.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentCommand {
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private final PaymentState state = PaymentState.FAILED;
}
