package com.bookstore.categories.query.services;

import com.bookstore.categories.entities.Category;
import com.bookstore.categories.query.query.FindAllCategoryQuery;
import com.bookstore.categories.query.query.FindByIdCategoryQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class CategoryQueryServices implements ICategoryQueryServices{
    private final QueryGateway queryGateway;
    private final MongoTemplate mongoTemplate;
    @Override
    public CompletableFuture<List<Category>> getAllCategory() {
        return this.queryGateway.query(new FindAllCategoryQuery(),
                ResponseTypes.multipleInstancesOf(Category.class));
    }

    @Override
    public CompletableFuture<Category> getCategoryById(String categoryId) {
        notNull(categoryId,"Category need to find not null");
        final Category categoryExist = mongoTemplate.findById(categoryId, Category.class);
        if (categoryExist == null){
            throw new RuntimeException("Category id = "+categoryId+" is not exist");
        }
        return this.queryGateway.query(new FindByIdCategoryQuery(categoryId),
                ResponseTypes.instanceOf(Category.class));
    }
}
