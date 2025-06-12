package com.ecommerce.dto.order;

import com.ecommerce.entity.userentity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class orderrequestdto {

    @NotNull(message = "User email is required")
    @Email(message = "Enter a valid email id")
    private String email;

    @NotNull(message = "Order items are required")
    @Size(min = 1, message = "At least one item is required")
    private Map<Long, Integer> items;

    @NotNull(message = "Shipping address is required")
    private String shippingAddress;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    @NotNull
    private Integer quantity;
}
