package com.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class deleteuserdto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
