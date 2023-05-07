package com.bookstore.cart.command.aggregate;

import com.bookstore.cart.command.command.IncrementBookCommand;
import com.bookstore.cart.command.events.BookInCartIncreasedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemLine {
    private String bookId;
    private BigDecimal price;
    private Integer quantity;
//    @CommandHandler
//    public void handle(IncrementBookCommand command){
//        AggregateLifecycle.apply(new BookInCartIncreasedEvent(
//                command.getCartId(),
//                command.getBookId(),
//                command.getPrice()
//        ));
//    }
//    @EventSourcingHandler
//    public void on(BookInCartIncreasedEvent event){
//        this.quantity+=event.getQuantity();
//        event.setQuantity(this.getQuantity());
//    }
}
