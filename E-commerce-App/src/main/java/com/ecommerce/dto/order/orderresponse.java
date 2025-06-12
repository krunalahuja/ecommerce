package com.ecommerce.dto.order;
import com.ecommerce.enums.status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class orderresponse {
    private String email;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
}
