package com.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class userregisterdto {

    @NotBlank
    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min=2,max = 50)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 16)
    private String password;

    @Size(min = 10, max = 15)
    private String phone;

    private String role;
}