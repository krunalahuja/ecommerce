package com.ecommerce.dto.product;

import lombok.Data;

@Data
public class productresponsedto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private int stock;
    private String category;
    private Boolean isavailable;
    private String sellername;
    private String selleid;
    private String brand;
}

