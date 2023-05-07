package com.bookstore.book.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDeletedByIdEvent {
    private String id;
}
