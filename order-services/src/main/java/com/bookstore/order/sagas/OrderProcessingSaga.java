package com.bookstore.order.sagas;

import com.bookstore.coreapis.command.*;
import com.bookstore.coreapis.common.StoreToken;
import com.bookstore.coreapis.enumaration.OrderStatus;
import com.bookstore.coreapis.enumaration.PaymentState;
import com.bookstore.coreapis.event.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.UUID;

@Saga
@NoArgsConstructor
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent saga with order id [{}]", event.getOrderId());
        try {
            String paymentId = UUID.randomUUID().toString();
            this.commandGateway.sendAndWait(new CreateInvoiceCommand(
                    paymentId,
                    event.getOrderId(),
                    event.getPaymentMethod(),
                    event.getTotalPrice()
            ));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            // Handle if err when create invoice failed
            this.cancelOrder(event.getOrderId());
        }
    }
    private void cancelOrder(String orderId){
        log.error("Create invoice failed -> Cancel order id : {}",orderId);
        this.commandGateway.send(new CancelOrderCommand(orderId));
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(InvoiceCreatedEvent event){
        log.info("InvoiceCreatedEvent saga with order id [{}]", event.getOrderId());
        try {
            sendShipCommand(event.getOrderId());
        }
        catch (Exception e){
            log.error(e.getLocalizedMessage());
            // Handle for cancel payment command
            this.cancelPayment(event.getPaymentId(), event.getOrderId());
        }
    }
    private void sendShipCommand(String orderId) {
        OrderShipmentCommand command = new OrderShipmentCommand(UUID.randomUUID().toString(),
                orderId);
        this.commandGateway.sendAndWait(command);
    }
    private void cancelPayment(String paymentId,String orderId){
        log.error("Create ship failed -> Cancel order id : {}",orderId);
        CancelPaymentCommand command = new CancelPaymentCommand(paymentId,orderId);
        this.commandGateway.send(command);
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent saga with order id [{}]", event.getOrderId());
        if (event.getState().equals(PaymentState.FAILED)){
            cancelPayment(event.getPaymentId(), event.getOrderId());
        } else {
        sendShipCommand(event.getOrderId());
        }
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ShipOrderedEvent event){
        log.info("ShipOrderedEvent saga with order id [{}]", event.getOrderId());
        CompleteOrderCommand command = new CompleteOrderCommand(event.getOrderId(),
                OrderStatus.APPROVED);
        this.commandGateway.send(command);
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent event){
        log.info("PaymentCancelledEvent saga with order id [{}]", event.getOrderId());
        cancelOrder(event.getOrderId());
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event){
        log.info("OrderCompletedEvent saga with order id [{}]", event.getOrderId());
    }
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCancelledEvent event){
        log.info("OrderCancelledEvent saga with order id [{}]", event.getOrderId());
    }
}
