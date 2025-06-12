package com.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "products")
public class productentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid;

    @NotNull
    private String productname;

    @NotNull
    @Min(1)
    private Long price;

    private String description;

    @NotNull
    @Min(1)
    private int stock;

    private String category;

    private Boolean isavailable = true;

    private String sellername;

    private String selleid;

    private String brand;

    private String email;

    private String sellerpassword;

}
