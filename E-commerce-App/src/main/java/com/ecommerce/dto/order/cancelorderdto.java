package com.ecommerce.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

@Data
@AllowNonPortable
@NoArgsConstructor
public class cancelorderdto {
    @NotNull(message = "Please enter order id")
    private Long id;

    @NotNull(message = "Please enter product id")
    private Long orderid;

    @NotNull
    @Email(message = "Please enter your email address")
    private String email;
}