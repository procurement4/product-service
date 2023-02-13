package com.procurement.apps.service;

import com.procurement.apps.model.ProductRequest;
import com.procurement.apps.model.UpdateStockRequest;
import com.procurement.apps.utils.ResponseAPI;

public interface ProductService {
    ResponseAPI getAllProduct();
    ResponseAPI getProductById(String productId);
    ResponseAPI createProduct(ProductRequest request);
    ResponseAPI updateProduct(ProductRequest request);
    ResponseAPI updateStock(UpdateStockRequest request);
}
