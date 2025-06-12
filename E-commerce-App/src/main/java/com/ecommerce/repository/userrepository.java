package com.ecommerce.repository;

import com.ecommerce.entity.userentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userrepository extends JpaRepository <userentity,Long>{
    userentity findByEmail(String email);
    userentity findByUsername(String username);
}
