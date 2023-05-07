package com.bookstore.book.query.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBookBySeries {
    private String series;
    private Integer page;
    private Integer size;
    private String sort;
}
