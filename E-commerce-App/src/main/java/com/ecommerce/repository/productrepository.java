package com.ecommerce.repository;

import com.ecommerce.entity.productentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productrepository extends JpaRepository<productentity,Long> {
    List<productentity> findByProductnameContainingIgnoreCase(String productname);
    List<productentity> findByEmail(String email);
}
