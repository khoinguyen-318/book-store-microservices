package com.bookstore.categories.command.aggregate;

import com.bookstore.categories.command.command.CreateCategoryCommand;
import com.bookstore.categories.command.command.DeleteCategoryCommand;
import com.bookstore.categories.command.command.UpdateCategoryCommand;
import com.bookstore.categories.command.event.CategoryCreatedEvent;
import com.bookstore.categories.command.event.CategoryDeletedEvent;
import com.bookstore.categories.command.event.CategoryUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

@Aggregate
@Getter
@AllArgsConstructor
@NoArgsConstructor // Required by Axon
public class CategoryAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private String slug;
    private Instant createdAt;
    private Instant modifiedAt;
    private String modifiedBy;
    private String createdBy;
    private boolean activated;

    /*
    - Handle for create category event
    */
    @CommandHandler
    public CategoryAggregate(CreateCategoryCommand command) {
        AggregateLifecycle.apply(new CategoryCreatedEvent(
                command.getId(),
                command.getName(),
                command.getSlug(),
                command.getCreatedAt(),
                command.getModifiedAt(),
                command.getModifiedBy(),
                command.getCreatedBy(),
                command.isActivated()
        ));
    }

    @EventSourcingHandler
    public void on(CategoryCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.createdAt = event.getCreatedAt();
        this.modifiedAt = event.getModifiedAt();
        this.modifiedBy = event.getModifiedBy();
        this.createdBy = event.getCreatedBy();
        this.activated = event.isActivated();
    }

    /*
        - Handle for update category event
     */
    @CommandHandler
    public void handle(UpdateCategoryCommand command) {
        AggregateLifecycle.apply(new CategoryUpdatedEvent(
                command.getId(),
                command.getName(),
                command.getSlug(),
                command.getCreatedAt(),
                command.getModifiedAt(),
                command.getModifiedBy(),
                command.getCreatedBy(),
                command.isActivated()
        ));
    }

    @EventSourcingHandler
    public void on(CategoryUpdatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.createdAt = event.getCreatedAt();
        this.modifiedAt = event.getModifiedAt();
        this.modifiedBy = event.getModifiedBy();
        this.createdBy = event.getCreatedBy();
        this.activated = event.isActivated();
    }

    /*
     * Handle for delete category event
     */
    @CommandHandler
    public void handle(DeleteCategoryCommand command){
        AggregateLifecycle.apply(new CategoryDeletedEvent(
                command.getId()
        ));
    }
    @EventSourcingHandler
    public void on(CategoryDeletedEvent event){
        this.id = event.getId();
    }
}
