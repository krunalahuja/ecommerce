package com.ecommerce.repository;


import com.ecommerce.entity.reviewentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface reviewrepository extends JpaRepository<reviewentity, Long> {
    List<reviewentity> findByProductProductid(Long productId);
}

