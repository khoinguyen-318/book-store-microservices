package com.bookstore.categories.command.model;

import lombok.NonNull;

public record CategoryDTO(
        @NonNull
        String name,
        String slug,
        boolean activated
) {
}
