package com.bookstore.order.repository;

import com.bookstore.order.entities.OrderLines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLinesRepository extends JpaRepository<OrderLines, String> {
}