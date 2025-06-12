package com.ecommerce.dto.order;


import com.ecommerce.entity.orderentity;
import com.ecommerce.entity.productentity;
import com.ecommerce.entity.userentity;
import com.ecommerce.enums.status;
import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "order_items")
@Data
public class orderitementity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    private orderentity order;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private productentity product;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private userentity user;

    private int quantity;

    private Long priceAtPurchase;

    @Enumerated(EnumType.STRING)
    private status status;
}