package com.ecommerce.dto.user;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class userresponsedto {

        private Long id;

        private String firstName;

        private String lastName;

        private String email;

        private String phone;

        private Boolean isActive;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

}

