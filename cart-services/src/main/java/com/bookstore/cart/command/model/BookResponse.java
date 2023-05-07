package com.bookstore.cart.command.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record BookResponse(
        String id,
        String title,
        String slug,
        String description,
        BigDecimal price,
        Set<String>imagesUrl,
        String categoryId,
        Set<String> author,
        String publisher,
        String series,
        Integer pageCount,
        Instant publicationDate,
        Instant createdAt,
        Instant modifiedAt,
        String modifiedBy,
        String createdBy,
        boolean activated
) {
}
