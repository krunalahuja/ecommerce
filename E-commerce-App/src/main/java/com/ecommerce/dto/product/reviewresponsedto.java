package com.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class reviewresponsedto {

    private String username;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}