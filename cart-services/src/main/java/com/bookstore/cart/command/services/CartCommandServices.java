package com.bookstore.cart.command.services;

import com.bookstore.cart.command.command.*;
import com.bookstore.cart.command.model.BookResponse;
import com.bookstore.cart.command.model.ItemDto;
import com.bookstore.cart.entities.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "carts")
public class CartCommandServices implements ICartCommandServices {
    private final CommandGateway commandGateway;
    private final WebClient webClient;
    private final MongoTemplate mongoTemplate;

    @Override
    @CachePut
    public CompletableFuture<Cart> addToCart(ItemDto book) throws ExecutionException, InterruptedException {
        log.info("Book will add to cart info [{}]", book);
        //Check if cart is exist
        final Optional<Cart> cartExist = getCartExist(book.customerId(), null);

        final BookResponse getBookFromBookServices = getBookFromServices(book.bookId()).get();
        if (Objects.isNull(getBookFromBookServices)) {
            throw new RuntimeException("Book id = " + book.bookId() + " not found!");
        }
        if (cartExist.isEmpty()) {
            createCart(book)
                    .thenApply(cartId->
                            this.commandGateway.send(new AddBookToCartCommand(
                            cartId,
                            book.customerId(),
                            book.bookId(),
                            getBookFromBookServices.price(),
                            book.quantity() == null ? 1 : book.quantity()
                    ))).get();
        }
        // if exist item in cart -> increase quantity + 1
        if (cartExist.get().getItems().stream().anyMatch(item -> item.getBookId().equalsIgnoreCase(book.bookId()))) {
            log.info("Book is exist in cart ==> update quantity");
            return this.commandGateway.send(new IncrementBookCommand(
                    cartExist.get().getId(),
                    book.bookId(),
                    book.quantity() == null ? 1 : book.quantity(),
                    getBookFromBookServices.price()
            ));
        }
        return this.commandGateway.send(new AddBookToCartCommand(
                cartExist.get().getId(),
                book.customerId(),
                book.bookId(),
                getBookFromBookServices.price(),
                book.quantity() == null ? 1 : book.quantity()
        ));
    }

    @Override
    @CacheEvict(allEntries = true)
    public CompletableFuture<Cart> removeAllItemInCart(String cartId) {
        log.info("Cart will empty");
        return this.commandGateway.send(new EmptyCartCommand(
                cartId
        ));
    }

    @Override
    @CacheEvict(key = "{#cartId,#bookId}")
    public CompletableFuture<Cart> removeItemInCart(String cartId, String bookId) {
        log.info("Book id [{}] will delete in cart id [{}]", bookId, cartId);
        return this.commandGateway.send(new RemoveOneBookInCartCommand(
                cartId,
                bookId
        ));
    }

    @Override
    @Caching(evict = @CacheEvict(key = "#cartId"),put = @CachePut)
    public CompletableFuture<Cart> updateItemInCart(String cartId, ItemDto book) {
        log.info("Cart will update quantity item [{}]", book);
        //Check if cart is exist
        final Optional<Cart> cartExist = getCartExist(null, cartId);
        if (cartExist.isEmpty()) {
            throw new RuntimeException("Cart not present");
        }
        return this.commandGateway.send(new UpdateItemInCartCommand(
                cartId,
                book.customerId(),
                book.bookId(),
                book.quantity()
        ));
    }

    @Async
    CompletableFuture<BookResponse> getBookFromServices(String bookId) {
        return CompletableFuture.supplyAsync(() -> this.webClient.get()
                .uri("http://localhost:8888/api/v1/books/book/{bookId}", bookId)
                .retrieve()
                .onStatus(HttpStatus::isError, err -> Mono.error(new RuntimeException("An error occurred!")))
                .bodyToMono(BookResponse.class)
                .onErrorResume(RuntimeException.class, err -> {
                    log.info("Book not present!");
                    return Mono.empty();
                })
                .block()
        );
    }
    private CompletableFuture<String> createCart(ItemDto book){
        String cartId = UUID.randomUUID().toString();
        this.commandGateway.send(new CreateCartCommand(
                cartId,
                book.customerId(),
                BigDecimal.ZERO
        ));
        return CompletableFuture.completedFuture(cartId);
    }
    private Optional<Cart> getCartExist(String customerId, String id) {
        final Cart cartExist = this.mongoTemplate
                .findOne(new Query(Criteria.where("customerId").is(customerId)), Cart.class);
        final Cart cartExist1 = this.mongoTemplate
                .findOne(new Query(Criteria.where("id").is(id)), Cart.class);
        return Optional.ofNullable(Objects.isNull(cartExist) ? cartExist1 : cartExist);
    }
}
