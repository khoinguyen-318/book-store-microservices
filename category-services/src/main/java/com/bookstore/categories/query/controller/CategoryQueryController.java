package com.bookstore.categories.query.controller;

import com.bookstore.categories.entities.Category;
import com.bookstore.categories.query.services.ICategoryQueryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryQueryController {
    private final ICategoryQueryServices categoryQueryServices;

    @GetMapping
    public CompletableFuture<List<Category>> getAllCategory(){
        return this.categoryQueryServices.getAllCategory();
    }
    @GetMapping("/{categoryId}")
    public CompletableFuture<Category> getCategoryById(@PathVariable String categoryId){
        return this.categoryQueryServices.getCategoryById(categoryId);
    }
}
