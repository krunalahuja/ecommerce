package com.ecommerce.repository;


import com.ecommerce.entity.sellerentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface sellerrepository extends JpaRepository<sellerentity, Long> {
    sellerentity findByEmail(String email);
}

