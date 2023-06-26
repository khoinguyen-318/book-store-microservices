package com.bookstore.payment.query.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindOnePayment {
    private String orderId;
}
