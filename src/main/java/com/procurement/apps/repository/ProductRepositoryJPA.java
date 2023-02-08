package com.procurement.apps.repository;

import com.procurement.apps.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepositoryJPA extends JpaRepository<Product, UUID> {
}
