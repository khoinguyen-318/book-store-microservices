package com.bookstore.payment.command.aggregate;

import com.bookstore.coreapis.command.CancelPaymentCommand;
import com.bookstore.coreapis.command.CreateInvoiceCommand;
import com.bookstore.coreapis.command.ProcessPaymentCommand;
import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.enumaration.PaymentState;
import com.bookstore.coreapis.event.InvoiceCreatedEvent;
import com.bookstore.coreapis.event.PaymentCancelledEvent;
import com.bookstore.coreapis.event.PaymentProcessedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
@Setter
@Getter
@NoArgsConstructor // required by Axon
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentState state;
    private BigDecimal totalPrice;

    @CommandHandler
    public PaymentAggregate(CreateInvoiceCommand command){
        AggregateLifecycle.apply(new InvoiceCreatedEvent(
           command.getPaymentId(),
           command.getOrderId(),
           command.getPaymentMethod(),
           PaymentState.CREATED,
           command.getTotalPrice()
        ));
    }
    @CommandHandler
    public void handle(CancelPaymentCommand command){
        AggregateLifecycle.apply(new PaymentCancelledEvent(
                command.getPaymentId(),
                command.getOrderId(),
                command.getState()
        ));
    }
    @CommandHandler
    public void handle(ProcessPaymentCommand command){
        AggregateLifecycle.apply(new PaymentProcessedEvent(
                command.getPaymentId(),
                command.getOrderId(),
                command.getState()
        ));
    }
    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){
        this.state = event.getState();
        this.paymentMethod = PaymentMethod.PAYPAL;
    }

    @EventSourcingHandler
    public void on(InvoiceCreatedEvent event){
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
        this.paymentMethod = event.getPaymentMethod();
        this.state = event.getState();
        this.totalPrice = event.getTotalPrice();
    }
    @EventSourcingHandler
    public void on(PaymentCancelledEvent event){
        this.paymentId = event.getPaymentId();
        this.state = event.getState();
    }
}
