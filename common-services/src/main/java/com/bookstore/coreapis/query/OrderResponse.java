package com.bookstore.coreapis.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String customer;
    private String address;
    private String phone;
    private String paymentMethod;
    private Set<Object> items;
    private BigDecimal totalPrice;
    private Date orderDate;
    private Map<String,String> status;
}
