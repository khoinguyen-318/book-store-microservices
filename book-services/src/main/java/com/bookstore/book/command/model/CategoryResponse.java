package com.bookstore.book.command.model;

import java.time.Instant;

public record CategoryResponse(
        String id,
        String name,
        String slug,
        Instant createdAt,
        Instant modifiedAt,
        String modifiedBy,
        String createdBy,
        boolean activated
) {
}
