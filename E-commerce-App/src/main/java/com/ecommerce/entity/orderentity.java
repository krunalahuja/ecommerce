package com.ecommerce.entity;

import com.ecommerce.entity.userentity;
import com.ecommerce.enums.status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class orderentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private userentity userid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", nullable = false)
    private productentity productid;

    private List<String> categories;

    private String shippingAddress;

    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private status status;

    private LocalDateTime orderDate;

    private double totalAmount;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<userentity> orderItems;
}

