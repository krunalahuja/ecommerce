package com.ecommerce.repository;

import com.ecommerce.dto.order.orderitemdto;
import com.ecommerce.entity.orderentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface orderrepository extends JpaRepository<orderentity,Long> {
    List<orderitemdto> findByOrderid(Long orderId);
}
