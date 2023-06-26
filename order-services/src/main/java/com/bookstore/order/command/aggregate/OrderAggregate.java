package com.bookstore.order.command.aggregate;

import com.bookstore.coreapis.command.CancelOrderCommand;
import com.bookstore.coreapis.command.CompleteOrderCommand;
import com.bookstore.coreapis.command.CreateOrderCommand;
import com.bookstore.coreapis.command.UpdateStatusCommand;
import com.bookstore.coreapis.enumaration.OrderStatus;
import com.bookstore.coreapis.enumaration.PaymentMethod;
import com.bookstore.coreapis.event.OrderCancelledEvent;
import com.bookstore.coreapis.event.OrderCompletedEvent;
import com.bookstore.coreapis.event.OrderCreatedEvent;
import com.bookstore.coreapis.event.OrderStatusUpdated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.Date;

@Aggregate
@Setter
@Getter
@NoArgsConstructor // Required by Axon
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String customerId;
    private String address;
    private String phone;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private Date orderedDate;
    private BigDecimal totalPrice;
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command){
        if (command.getItems().isEmpty()){
            throw new RuntimeException("No item for create order");
        }
        AggregateLifecycle.apply(new OrderCreatedEvent(
                command.getOrderId(),
                command.getCustomerId(),
                command.getAddress(),
                command.getPhone(),
                command.getPaymentMethod(),
                command.getItems(),
                command.getStatus(),
                command.getTotalPrice()
        ));
    }
    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.address = event.getAddress();
        this.phone = event.getPhone();
        this.paymentMethod = event.getPaymentMethod();
        this.status = event.getStatus();
        this.orderedDate = event.getOrderedDate();
        this.totalPrice = event.getTotalPrice();
    }
    @CommandHandler
    public void handle(CancelOrderCommand command){
        AggregateLifecycle.apply(new OrderCancelledEvent(
                command.getOrderId(),
                command.getOrderStatus()
        ));
    }
    @EventSourcingHandler
    public void on(OrderCancelledEvent event){
        this.status = event.getOrderStatus();
    }
    @CommandHandler
    public void handle(CompleteOrderCommand command){
        AggregateLifecycle.apply(new OrderCompletedEvent(
                command.getOrderId(),
                command.getStatus()
        ));
    }
    @EventSourcingHandler
    public void on(OrderCompletedEvent event){
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(UpdateStatusCommand command){
        AggregateLifecycle.apply(new OrderStatusUpdated(
                command.getOrderId(),
                command.getStatus()
        ));
    }
    @EventSourcingHandler
    public void on(OrderStatusUpdated event){
        this.status = event.getStatus();
    }
}
