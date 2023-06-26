package com.bookstore.shipment.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
    @Query("select s from Shipment s where s.orderId = ?1")
    Optional<Shipment> findByOrderId(String orderId);
}