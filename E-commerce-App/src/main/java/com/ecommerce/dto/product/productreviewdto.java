package com.ecommerce.dto.product;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class productreviewdto {

    @Min(value = 1,message = "minimum rating is 1")
    @Max(value = 5,message = "maximum rating is 5")
    private Long rating;

    @Column(length = 1000)
    private String comment;
}
