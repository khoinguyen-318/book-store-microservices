package com.bookstore.cart.command.aggregate;

import com.bookstore.cart.command.command.*;
import com.bookstore.cart.command.events.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Aggregate
@Getter
@AllArgsConstructor
@NoArgsConstructor // Required by Axon
public class CartAggregate {
    @AggregateIdentifier
    private String cartId;
    private String customerId;
    private Set<ItemLine> items;
    private BigDecimal totalPrice;

    @CommandHandler
    public CartAggregate(CreateCartCommand command){
        AggregateLifecycle.apply(new CartCreatedEvent(
                command.getCartId(),
                command.getCustomerId(),
                command.getTotalPrice()
        ));
    }
    @EventSourcingHandler
    public void on(CartCreatedEvent event){
        this.items = new HashSet<>();
        this.cartId = event.getCartId();
        this.customerId = event.getCustomerId();
        this.totalPrice = BigDecimal.ZERO;
    }

    @CommandHandler
    public void handle(AddBookToCartCommand command) {
        AggregateLifecycle.apply(new BookAddedToCartEvent(
                this.getCartId(),
                this.getCustomerId(),
                command.getBookId(),
                command.getQuantity(),
                command.getPrice()
        ));
    }

    @EventSourcingHandler
    public void on(BookAddedToCartEvent event) {
        this.items.add(new ItemLine(event.getBookId(), event.getPrice(), event.getQuantity()));
        this.customerId = event.getCustomerId();
        this.items.forEach(item ->
                this.totalPrice = totalPrice
                        .add((item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))))
        );
    }
    @CommandHandler
    public void handle(EmptyCartCommand command){
        AggregateLifecycle.apply(new CartRemovedAllItemEvent(
                command.getCartId()
        ));
    }
    @EventSourcingHandler
    public void on(CartRemovedAllItemEvent event){
        this.cartId = event.getCartId();
        this.items.clear();
        this.totalPrice = BigDecimal.ZERO;
    }
    @CommandHandler
    public void handle(RemoveOneBookInCartCommand command){
        AggregateLifecycle.apply(new OneBookDeletedEvent(
            command.getCartId(),
            command.getBookId()
        ));
    }
    @EventSourcingHandler
    public void on(OneBookDeletedEvent event){
        this.cartId = event.getCartId();
        this.items.removeIf(itemLine -> itemLine.getBookId().equalsIgnoreCase(event.getBookId()));
        this.items.forEach(item ->
                this.totalPrice = totalPrice
                        .add((item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))))
        );
    }
    @CommandHandler
    public void handle(UpdateItemInCartCommand command){
        if (command.getQuantity()==0){
            AggregateLifecycle.apply(new OneBookDeletedEvent(
                    command.getCartId(),
                    command.getBookId()
            ));
        }
        AggregateLifecycle.apply(new ItemInCartUpdatedEvent(
                command.getCartId(),
                command.getCustomerId(),
                command.getBookId(),
                command.getQuantity()
        ));
    }
    @EventSourcingHandler
    public void on(ItemInCartUpdatedEvent event){
        this.cartId = event.getCartId();
        this.customerId =event.getCustomerId();
        BigDecimal tempPrice = BigDecimal.ZERO;
        Iterator<ItemLine> iterator = this.items.iterator();
        while (iterator.hasNext()){
            var item =iterator.next();
            if (item.getBookId().equalsIgnoreCase(event.getBookId())){
                tempPrice = item.getPrice();
                iterator.remove();
                break;
            }
        }
        this.items.add(new ItemLine(event.getBookId(), tempPrice, event.getQuantity()));
    }
    @CommandHandler
    public void handle(IncrementBookCommand command){
        AggregateLifecycle.apply(new BookInCartIncreasedEvent(
                this.getCartId(),
                command.getBookId(),
                command.getQuantity(),
                command.getPrice()
        ));
    }
    @EventSourcingHandler
    public void on(BookInCartIncreasedEvent event){
        this.cartId = event.getCartId();
        Iterator<ItemLine> iterator = this.items.iterator();
        while (iterator.hasNext()){
            var item =iterator.next();
            if (item.getBookId().equalsIgnoreCase(event.getBookId())){
                item.setQuantity(item.getQuantity()+event.getQuantity());
                break;
            }
        }
        this.items.forEach(item ->
                this.totalPrice = totalPrice
                        .add((item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))))
        );
    }

}
