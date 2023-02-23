package com.procurement.apps.controller;

import com.procurement.apps.model.ProductRequest;
import com.procurement.apps.model.UpdateStockRequest;
import com.procurement.apps.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;
    @Value("${BASE_URL}")
    private String BASE_URL;
    @Value("[product-service]")
    private String SERVICE_NAME;
    @GetMapping
    public ResponseEntity hello(){
        return new ResponseEntity("Product-Service is online", HttpStatus.OK);
    }

    @GetMapping("/v1/products")
    public ResponseEntity getAllProduct(){
        log.info(String.format("%s GET /v1/products is called",SERVICE_NAME));
        var result = productService.getAllProduct();
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity getProductById(@PathVariable String productId){
        log.info(String.format("$s GET /v1/products/{productId} is called", SERVICE_NAME));
        var result = productService.getProductById(productId);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PostMapping("/v1/products")
    public ResponseEntity createProduct(@RequestBody ProductRequest request){
        log.info(String.format("%s POST /v1/products is called", SERVICE_NAME));
        var result = productService.createProduct(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PostMapping("/v1/update_products")
    public ResponseEntity updateProduct(@RequestBody ProductRequest request){
        log.info(String.format("%s PATCH /v1/products is called", SERVICE_NAME));
        var result = productService.updateProduct(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PatchMapping("/v1/products/update_stock")
    public ResponseEntity updateStock(@RequestBody UpdateStockRequest request){
        log.info(String.format("%s PATCH /v1/products/update_stock is called", SERVICE_NAME));
        var result = productService.updateStock(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }
}
