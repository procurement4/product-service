package com.procurement.apps.repository;

import com.procurement.apps.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface ProductRepositoryJPA extends JpaRepository<Product, UUID> {
    @Transactional
    @Modifying
    @Query(value = "update products set stock = :stock, updated_at = :updated_at, updated_by = :user_id where id = :product_id", nativeQuery = true)
    void updateStock(@Param("stock") int stock, @Param("user_id") String user_id, @Param("updated_at") Date updated_at, @Param("product_id") UUID product_id);
}
