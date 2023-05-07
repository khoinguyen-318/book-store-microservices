package com.bookstore.categories.command.controller;

import com.bookstore.categories.command.model.CategoryDTO;
import com.bookstore.categories.command.services.ICategoryCommandServices;
import com.bookstore.categories.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryCommandController {
    private final ICategoryCommandServices commandServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Category> addCategory(@Validated @RequestBody CategoryDTO category){
        return this.commandServices.createCategory(category);
    }

    @PutMapping("/{categoryId}")
    public CompletableFuture<Category> updateCategory(@Validated @RequestBody CategoryDTO category,
                                                      @PathVariable String categoryId){
        return this.commandServices.updateCategory(categoryId,category);
    }
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Category> deleteByIdCategory(@PathVariable String categoryId){
        return this.commandServices.deleteByIdCategory(categoryId);
    }
}
