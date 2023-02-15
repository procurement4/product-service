package com.procurement.apps.repository;

import com.procurement.apps.entity.Product;
import com.procurement.apps.model.ProductRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
//@CacheConfig(cacheNames = {"products"})
public class ProductRepository {
    @Autowired
    private final ProductRepositoryJPA productRepositoryJPA;
    private final String HASH_VALUE = "Product";

    @Cacheable(value = HASH_VALUE ,key = "#id")
    public Optional<Product> findById(UUID id){
        log.info("[LOG] getProductById is called from database");
        return productRepositoryJPA.findById(id);
    }

    @Cacheable(value = HASH_VALUE)
    public List<Product> findAll(){
        log.info("[LOG] getAllProduct is called from database");
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
