package com.bookstore.book.command.model;

import lombok.NonNull;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record BookDto(
        @NonNull
        String title,
        String slug,
        String description,
        @Min(0)
        BigDecimal price,
        @NonNull
        String categoryId,
        Set<String> author,
        String publisher,
        String series,
        @Min(0)
        Integer pageCount,
        Instant publicationDate,
        boolean activated
        ) {
}
