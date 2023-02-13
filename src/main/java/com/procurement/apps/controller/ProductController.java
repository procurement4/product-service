package com.procurement.apps.controller;

import com.procurement.apps.model.ProductRequest;
import com.procurement.apps.model.UpdateStockRequest;
import com.procurement.apps.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    @Value("${BASE_URL}")
    private String BASE_URL;
    @Value("[product-service]")
    private String SERVICE_NAME;
    @Value("${sm://greeting}")
    private String test;
    @Value("${sm://POSTGRES_URL}")
    private String test2;
    @GetMapping
    public ResponseEntity hello(){
        return new ResponseEntity(test + " === " + test2, HttpStatus.OK);
    }

    @GetMapping("/v1/products")
    public ResponseEntity getAllProduct(){
        var result = productService.getAllProduct();
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity getProductById(@PathVariable String productId){
        var result = productService.getProductById(productId);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PostMapping("/v1/products")
    public ResponseEntity createProduct(@RequestBody ProductRequest request){
        var result = productService.createProduct(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PatchMapping("/v1/products")
    public ResponseEntity updateProduct(@RequestBody ProductRequest request){
        var result = productService.updateProduct(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }

    @PatchMapping("/v1/products/update_stock")
    public ResponseEntity updateStock(@RequestBody UpdateStockRequest request){
        var result = productService.updateStock(request);
        return ResponseEntity.status(result.getCode()).body(result);
    }
}
