package com.bookstore.categories.query.services;

import com.bookstore.categories.entities.Category;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategoryQueryServices {
    CompletableFuture<List<Category>> getAllCategory();

    CompletableFuture<Category> getCategoryById(String categoryId);
}
