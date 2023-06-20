package com.bookstore.payment.command.projection;

import com.bookstore.coreapis.event.InvoiceCreatedEvent;
import com.bookstore.coreapis.event.PaymentCancelledEvent;
import com.bookstore.coreapis.event.PaymentProcessedEvent;
import com.bookstore.payment.entities.Payment;
import com.bookstore.payment.entities.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProjector {
    private final PaymentRepository paymentRepository;
    @EventHandler
    public void on(InvoiceCreatedEvent event){
        Payment payment = Payment.builder()
                .id(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentMethod(event.getPaymentMethod())
                .state(event.getState())
                .totalPrice(event.getTotalPrice())
                .build();
        this.paymentRepository.save(payment);
    }
    @EventHandler
    public void on(PaymentCancelledEvent event){
        final Optional<Payment> payment = this.paymentRepository.findById(event.getPaymentId());
        payment.ifPresent((existPayment)->{
            existPayment.setState(event.getState());
            this.paymentRepository.save(existPayment);
        });
    }
    @EventHandler
    public void on(PaymentProcessedEvent event){
        final Optional<Payment> payment = this.paymentRepository.findByOrderId(event.getOrderId());
        payment.ifPresent((existPayment)->{
            existPayment.setState(event.getState());
            this.paymentRepository.save(existPayment);
        });
    }
}
