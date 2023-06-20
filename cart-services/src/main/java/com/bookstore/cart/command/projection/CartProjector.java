package com.bookstore.cart.command.projection;

import com.bookstore.cart.command.events.*;
import com.bookstore.cart.entities.Cart;
import com.bookstore.cart.entities.Item;
import com.bookstore.cart.query.query.FindAllItemInCart;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CartProjector {
    private final MongoTemplate mongoTemplate;

    @EventHandler
    public void on(CartCreatedEvent event) {
        Cart cart = Cart.builder()
                .id(event.getCartId())
                .customerId(event.getCustomerId())
                .items(new HashSet<>())
                .totalPrice(event.getTotalPrice())
                .build();
        this.mongoTemplate.save(cart);
    }

    @EventHandler
    public void on(BookAddedToCartEvent event) {
        Item item = new Item(event.getBookId(), event.getPrice(),event.getQuantity());
        Set<Item> items = new HashSet<>();
        Query query = new Query(Criteria.where("id").is(event.getCartId()));
        final Cart cartExist = this.mongoTemplate.findOne(query, Cart.class);
        if (Objects.nonNull(cartExist)){
            items.addAll(cartExist.getItems());
            items.add(item);
        }
        Cart cart = Cart.builder()
                .id(event.getCartId())
                .customerId(event.getCustomerId())
                .items(items)
                .totalPrice(calculateTotalPrice(items))
                .build();
        Document object = new Document();
        mongoTemplate.getConverter().write(cart, object);
        Update update = Update.fromDocument(object);
        this.mongoTemplate.updateFirst(query, update, Cart.class);

    }

    @EventHandler
    public void on(BookInCartIncreasedEvent event) {
        final Cart existCart = this.mongoTemplate.findById(event.getCartId(), Cart.class);
        if (Objects.nonNull(existCart)) {
            Set<Item> items = existCart.getItems();
            Iterator<Item> iterator = items.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (item.getBookId().equalsIgnoreCase(event.getBookId())) {
                    item.setQuantity(item.getQuantity() + event.getQuantity());
                    break;
                }
            }
            Cart newCart = Cart.builder()
                    .id(existCart.getId())
                    .customerId(existCart.getCustomerId())
                    .items(items)
                    .totalPrice(calculateTotalPrice(items))
                    .build();
            Document object = new Document();
            mongoTemplate.getConverter().write(newCart, object);
            Update update = Update.fromDocument(object);
            this.mongoTemplate
                    .updateFirst(new Query(Criteria.where("id").is(event.getCartId())),
                            update, Cart.class);
        }
    }
    @EventHandler
    public void on(CartRemovedAllItemEvent event){
        final Cart existCart = this.mongoTemplate.findById(event.getCartId(), Cart.class);
        if (Objects.nonNull(existCart)) {
            Cart newCart = Cart.builder()
                    .id(existCart.getId())
                    .customerId(existCart.getCustomerId())
                    .items(new HashSet<>(0))
                    .totalPrice(BigDecimal.ZERO)
                    .build();
            Document object = new Document();
            mongoTemplate.getConverter().write(newCart, object);
            Update update = Update.fromDocument(object);
            this.mongoTemplate
                    .updateFirst(new Query(Criteria.where("id").is(event.getCartId())),
                            update, Cart.class);
        }
    }
    @EventHandler
    public void on(OneBookDeletedEvent event){
        final Cart existCart = this.mongoTemplate.findById(event.getCartId(), Cart.class);
        if (Objects.nonNull(existCart)){
            Set<Item> oldItems = existCart.getItems();
            oldItems.removeIf(itemLine -> itemLine.getBookId().equalsIgnoreCase(event.getBookId()));
            Cart newCart = Cart.builder()
                    .id(existCart.getId())
                    .customerId(existCart.getCustomerId())
                    .items(oldItems)
                    .totalPrice(calculateTotalPrice(oldItems))
                    .build();
            Document object = new Document();
            mongoTemplate.getConverter().write(newCart, object);
            Update update = Update.fromDocument(object);
            this.mongoTemplate
                    .updateFirst(new Query(Criteria.where("id").is(event.getCartId())),
                            update, Cart.class);
        }
    }
    @EventHandler
    public void on(ItemInCartUpdatedEvent event){
        BigDecimal tempPrice = BigDecimal.ZERO;
        final Cart existCart = this.mongoTemplate.findById(event.getCartId(), Cart.class);
        if (Objects.nonNull(existCart)){
            Set<Item> oldItems = existCart.getItems();
            Iterator<Item> iterator = oldItems.iterator();
            while (iterator.hasNext()){
                var item = iterator.next();
                if (item.getBookId().equalsIgnoreCase(event.getBookId())){
                    tempPrice = item.getPrice();
                    iterator.remove();
                }
            }
            oldItems.add(new Item(event.getBookId(), tempPrice, event.getQuantity()));
            Cart newCart = Cart.builder()
                    .id(existCart.getId())
                    .customerId(existCart.getCustomerId())
                    .items(oldItems)
                    .totalPrice(calculateTotalPrice(oldItems))
                    .build();
            Document object = new Document();
            mongoTemplate.getConverter().write(newCart, object);
            Update update = Update.fromDocument(object);
            this.mongoTemplate
                    .updateFirst(new Query(Criteria.where("id").is(event.getCartId())),
                            update, Cart.class);
        }
    }
    @QueryHandler
    public Cart handle(FindAllItemInCart query){
        return this.mongoTemplate.findOne(new Query(Criteria.where("customerId").is(query.getCustomerId())),Cart.class);
    }
    private BigDecimal calculateTotalPrice(Set<Item> items){
        BigDecimal price = BigDecimal.ZERO;
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()){
            var item = iterator.next();
            price = price.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return price;
    }
}
