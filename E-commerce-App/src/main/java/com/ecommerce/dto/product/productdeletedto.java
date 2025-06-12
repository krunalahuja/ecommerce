package com.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class productdeletedto {

    @NotNull
    private Long productId;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String sellerpassword;
}
