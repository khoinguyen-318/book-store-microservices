package com.bookstore.order.entities;

import com.bookstore.coreapis.enumaration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private String customerId;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderLines> orderLines;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Date orderedDate;
    private BigDecimal totalPrice;

}
