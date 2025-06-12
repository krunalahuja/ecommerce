package com.ecommerce.dto.order;

import com.ecommerce.enums.status;
import jakarta.persistence.*;
import lombok.Data;


@Data
public class orderitemdto {
    @Id
    private Long order_id;

    @OneToOne(cascade = CascadeType.ALL)
    private Long productid;
    private Long userid;
    private int quantity;
    private Long priceAtPurchase;
    private status status;
}