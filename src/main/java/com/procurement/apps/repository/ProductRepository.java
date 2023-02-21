package com.procurement.apps.repository;

import com.procurement.apps.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepository {
    @Autowired
    private final ProductRepositoryJPA productRepositoryJPA;
    private final String HASH_VALUE = "Product";
    @Value("[product-service]")
    private String SERVICE_NAME;

    @Cacheable(value = HASH_VALUE ,key = "#id")
    public Optional<Product> findById(UUID id){
        log.info(String.format("%s getProductById is called from database",SERVICE_NAME));
        return productRepositoryJPA.findById(id);
    }

    @Cacheable(value = HASH_VALUE)
    public List<Product> findAll(){
        log.info(String.format("%s getAllProduct is called from database",SERVICE_NAME));
        return productRepositoryJPA.findAll();
    }

    @Transactional
    @CacheEvict(value = HASH_VALUE, key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    public Product save(Product product){
        return productRepositoryJPA.save(product);
    }

    @CachePut(value = HASH_VALUE, key = "#product.id")
    @CacheEvict(value = HASH_VALUE, key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
    public Product update(Product product){
        return productRepositoryJPA.save(product);
    }
}
