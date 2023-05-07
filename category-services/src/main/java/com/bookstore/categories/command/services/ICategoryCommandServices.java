package com.bookstore.categories.command.services;

import com.bookstore.categories.command.model.CategoryDTO;
import com.bookstore.categories.entities.Category;

import java.util.concurrent.CompletableFuture;

public interface ICategoryCommandServices {
    CompletableFuture<Category> createCategory(CategoryDTO category);

    CompletableFuture<Category> updateCategory(String categoryId, CategoryDTO category);

    CompletableFuture<Category> deleteByIdCategory(String categoryId);
}
