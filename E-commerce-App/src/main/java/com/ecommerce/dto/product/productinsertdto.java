package com.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class productinsertdto {

    @NotNull
    private String name;

    @NotNull
    @Min(1)
    private Long price;

    private String description;

    @NotNull
    @Min(1)
    private int stock;

    private String category;

    private String brand;

    @NotNull
    private String sellername;

    @NotNull
    private String selleid;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String sellerpassword;
}

