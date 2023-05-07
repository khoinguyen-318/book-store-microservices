package com.bookstore.categories.command.services;

import com.bookstore.categories.command.command.CreateCategoryCommand;
import com.bookstore.categories.command.command.DeleteCategoryCommand;
import com.bookstore.categories.command.command.UpdateCategoryCommand;
import com.bookstore.categories.command.model.CategoryDTO;
import com.bookstore.categories.entities.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryCommandServices implements ICategoryCommandServices{
    private final CommandGateway commandGateway;
    private final MongoTemplate mongoTemplate;
    @Override
    public CompletableFuture<Category> createCategory(CategoryDTO category) {
        log.info("Create Category information: [{}]",category);
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(category.name()));
        final List<Category> categoryList = mongoTemplate.find(query, Category.class);
        if(categoryList.size()>0){
            throw new RuntimeException("Name "+category.name()+ " is exist");
        }
        return this.commandGateway.send(new CreateCategoryCommand(
                UUID.randomUUID().toString(),
                category.name(),
                category.slug(),
                Instant.now(),
                null,
                null,
                "Admin", // Will be replaced by user authenticated
                true
        ));
    }

    @Override
    public CompletableFuture<Category> updateCategory(String categoryId, CategoryDTO category) {
        notNull(categoryId,"Category need to update not null");
        log.info("Update category id = [{}] - has info: [{}]",categoryId,category);
        final Category categoryExist = mongoTemplate.findById(categoryId, Category.class);
        if (categoryExist == null){
            throw new RuntimeException("Category id = "+categoryId+" is not exist");
        }
        return this.commandGateway.send(new UpdateCategoryCommand(
                categoryId,
                category.name(),
                category.slug(),
                categoryExist.getCreatedAt(),
                Instant.now(),
                "Admin", // Will be replaced by user authenticated
                categoryExist.getCreatedBy(),
                category.activated()
        ));
    }

    @Override
    public CompletableFuture<Category> deleteByIdCategory(String categoryId) {
        notNull(categoryId,"Category need to deleted not null");
        log.info("Delete category id = [{}] ",categoryId);
        final Category categoryExist = mongoTemplate.findById(categoryId, Category.class);
        if (categoryExist == null){
            throw new RuntimeException("Category id = "+categoryId+" is not exist");
        }
        return this.commandGateway.send(new DeleteCategoryCommand(
                categoryId
        ));
    }
}
