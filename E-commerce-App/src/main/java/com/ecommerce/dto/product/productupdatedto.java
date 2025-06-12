package com.ecommerce.dto.product;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class productupdatedto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Long newPrice;

    @NotNull
    @Min(0)
    private Integer newStock;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String sellerpassword;
}

