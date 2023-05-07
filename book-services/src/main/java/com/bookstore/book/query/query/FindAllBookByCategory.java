package com.bookstore.book.query.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBookByCategory {
    private String categoryId;
    private Integer page;
    private Integer size;
    private String sort;
}
