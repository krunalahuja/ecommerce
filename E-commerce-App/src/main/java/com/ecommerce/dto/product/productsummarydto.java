package com.ecommerce.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class productsummarydto {

    private Long id;

    private String name;

    private String category;

    private String description;

    private Double price;
}
