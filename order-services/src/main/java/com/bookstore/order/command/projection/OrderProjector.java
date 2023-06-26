package com.bookstore.order.command.projection;

import com.bookstore.coreapis.event.OrderCancelledEvent;
import com.bookstore.coreapis.event.OrderCompletedEvent;
import com.bookstore.coreapis.event.OrderCreatedEvent;
import com.bookstore.coreapis.event.OrderStatusUpdated;
import com.bookstore.order.entities.Order;
import com.bookstore.order.entities.OrderLines;
import com.bookstore.order.query.query.FindAllOrder;
import com.bookstore.order.query.query.FindOrder;
import com.bookstore.order.repository.OrderLinesRepository;
import com.bookstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderProjector {
    private final OrderRepository orderRepository;
    private final OrderLinesRepository orderLinesRepository;
    @EventHandler
    @Transactional
    public void on(OrderCreatedEvent event){
        Order order = new Order();
        Set<OrderLines> orderLines = new HashSet<>();
        order.setId(event.getOrderId());
        order.setCustomerId(event.getCustomerId());
        order.setAddress(event.getAddress());
        order.setPhone(event.getPhone());
        order.setStatus(event.getStatus());
        event.getItems().forEach(item->{
            final OrderLines lines = new OrderLines(item.getBookId(), item.getQuantity(), item.getPrice(),order);
            orderLines.add(lines);
        });
        order.setOrderLines(orderLines);
        order.setOrderedDate(event.getOrderedDate());
        order.setTotalPrice(event.getTotalPrice());
        orderRepository.save(order);
    }
    @EventHandler
    public void on(OrderCancelledEvent event){
        final Optional<Order> order = this.orderRepository.findById(event.getOrderId());
        order.ifPresent((existOrder)->{
            existOrder.setStatus(event.getOrderStatus());
            this.orderRepository.save(existOrder);
        });
    }
    @EventHandler
    public void on(OrderCompletedEvent event){
        final Optional<Order> order = this.orderRepository.findById(event.getOrderId());
        order.ifPresent((existOrder)->{
            existOrder.setStatus(event.getStatus());
            this.orderRepository.save(existOrder);
        });
    }
    @EventHandler
    public void on(OrderStatusUpdated event){
        final Optional<Order> order = this.orderRepository.findById(event.getOrderId());
        order.ifPresent((existOrder)->{
            existOrder.setStatus(event.getStatus());
            this.orderRepository.save(existOrder);
        });
    }



    //Handle for read
    @QueryHandler
    public List<Order> getAllOrder(FindAllOrder query){
        return this.orderRepository.findAll();
    }
    @QueryHandler
    public Order getOrder(FindOrder query){
        return this.orderRepository.findById(query.getOrderId()).orElseGet(null);
    }

}
