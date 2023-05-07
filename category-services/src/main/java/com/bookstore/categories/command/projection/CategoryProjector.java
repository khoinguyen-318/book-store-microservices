package com.bookstore.categories.command.projection;

import com.bookstore.categories.command.event.CategoryCreatedEvent;
import com.bookstore.categories.command.event.CategoryDeletedEvent;
import com.bookstore.categories.command.event.CategoryUpdatedEvent;
import com.bookstore.categories.entities.Category;
import com.bookstore.categories.query.query.FindAllCategoryQuery;
import com.bookstore.categories.query.query.FindByIdCategoryQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bookstore.categories.entities.Category.*;

@Component
@RequiredArgsConstructor
public class CategoryProjector {
    private final MongoTemplate mongoTemplate;

    @EventHandler
    public void on(CategoryCreatedEvent event) {
        Category category = builder()
                .id(event.getId())
                .name(event.getName())
                .slug(event.getSlug())
                .createdAt(event.getCreatedAt())
                .modifiedAt(event.getModifiedAt())
                .createdBy(event.getCreatedBy())
                .modifiedBy(event.getModifiedBy())
                .activated(event.isActivated())
                .build();
        mongoTemplate.insert(category);
    }

    @EventHandler
    public void on(CategoryUpdatedEvent event) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(event.getId()));
        Update update = new Update();
        update.set("name",event.getName());
        update.set("slug",event.getSlug());
        update.set("modifiedAt",event.getModifiedAt());
        update.set("modifiedBy",event.getModifiedBy());
        update.set("activated",event.isActivated());
        mongoTemplate.updateFirst(query,update,Category.class);
    }
    @EventHandler
    public void on(CategoryDeletedEvent event) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(event.getId()));
        mongoTemplate.remove(query,Category.class);
    }
    @QueryHandler
    public Category handle(FindByIdCategoryQuery query){
        return mongoTemplate.findById(query.getId(),Category.class);
    }
    @QueryHandler
    public List<Category> handle(FindAllCategoryQuery query){
        return mongoTemplate.findAll(Category.class);
    }
}
