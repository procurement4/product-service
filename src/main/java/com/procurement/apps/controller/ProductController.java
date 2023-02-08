package com.procurement.apps.controller;

import com.procurement.apps.model.ProductRequest;
import com.procurement.apps.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
}
