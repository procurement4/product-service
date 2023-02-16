package com.procurement.apps.service;

import com.google.gson.Gson;
import com.procurement.apps.entity.Product;
import com.procurement.apps.model.ProductRequest;
import com.procurement.apps.model.ProductResponse;
import com.procurement.apps.model.UpdateStockRequest;
import com.procurement.apps.repository.ProductRepository;
import com.procurement.apps.repository.ProductRepositoryJPA;
import com.procurement.apps.utils.ResponseAPI;
import com.procurement.apps.utils.ValidationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryJPA productRepositoryJPA;
    private final ProductRepository productRepository;
    private final ResponseAPI responseAPI;
    @Autowired
    private final ModelMapper modelMapper;
    @Value("[product-service]")
    private String SERVICE_NAME;

    public ResponseAPI getAllProduct(){
        try {
            log.info(String.format("%s productService.getAllProduct is called", SERVICE_NAME));
            var getAllProduct = productRepository.findAll().stream().filter(x -> x.getIs_deleted().equals(false));
            var data = getAllProduct.map(x -> modelMapper.map(x, ProductResponse.class)).toList();
            log.info(String.format("%s Result : %s", SERVICE_NAME, new Gson().toJson(data)));
            return responseAPI.OK("Success get data", data);
        }catch (Exception ex){
            var errMsg = String.format("Error Message : %s with Stacktrace : %s",ex.getMessage(),ex.getStackTrace());
            log.error(String.format("%s" , errMsg));
            return responseAPI.INTERNAL_SERVER_ERROR(errMsg,null);
        }
    }

    public ResponseAPI getProductById(String productId){
        try {
            log.info(String.format("%s productService.getProductById is called", SERVICE_NAME));
            var id = UUID.fromString(productId);
            var getProductById = productRepository.findById(id).filter(x -> x.getIs_deleted().equals(false));
            var data = modelMapper.map(getProductById, ProductResponse.class);
            log.info(String.format("%s Result : %s", SERVICE_NAME, new Gson().toJson(data)));
            if (getProductById.isEmpty()) return responseAPI.NOT_FOUND("Product not found", null);
            return responseAPI.OK("Success get data", data);
        }catch (Exception ex){
            var errMsg = String.format("Error Message : %s with Stacktrace : %s",ex.getMessage(),ex.getStackTrace());
            log.error(String.format("%s" , errMsg));
            return responseAPI.INTERNAL_SERVER_ERROR(errMsg,null);
        }
    }

    public ResponseAPI createProduct(ProductRequest request){
        try {
            //Validate request
            log.info(String.format("%s productService.createProduct is called", SERVICE_NAME));
            var validate = new ValidationRequest(request).validate();
            if (validate.size() > 0){
                log.info(String.format("Validate Error : %s", validate.toString()));
                return responseAPI.BAD_REQUEST(validate.toString(), null);
            }

            var newProduct = modelMapper.map(request, Product.class);
            newProduct.setCreated_at(new Date());
            newProduct.setUpdated_at(new Date());
            newProduct.setCreated_by(request.getUser_id().toString());
            newProduct.setUpdated_by(request.getUser_id().toString());
            productRepository.save(newProduct);

            //Check data saved
            var getProductById = productRepository.findById(newProduct.getId());
            if (getProductById.isEmpty()){
                log.info(String.format("%s Product not found", SERVICE_NAME));
                return responseAPI.INTERNAL_SERVER_ERROR("Failed create new product", null);
            }

            log.info(String.format("%s Data successfully created", SERVICE_NAME));
            var data = modelMapper.map(getProductById, ProductResponse.class);
            return responseAPI.CREATED("Success create new user", data);
        }catch (Exception ex){
            var errMsg = String.format("Error Message : %s with Stacktrace : %s",ex.getMessage(),ex.getStackTrace());
            log.error(String.format("%s" , errMsg));
            return responseAPI.INTERNAL_SERVER_ERROR(errMsg,null);
        }
    }

    public ResponseAPI updateProduct(ProductRequest request){
        try {
            //Validate request
            log.info(String.format("%s productService.updateProduct is called", SERVICE_NAME));
            if (request.getId() == null) return responseAPI.INTERNAL_SERVER_ERROR("[id must not be blank]", null);
            var id = UUID.fromString(request.getId());
            var validate = new ValidationRequest(request).validate();
            if (validate.size() > 0) return responseAPI.BAD_REQUEST(validate.toString(), null);

            var getProductById = productRepository.findById(id);
            if (getProductById.isEmpty()) return responseAPI.INTERNAL_SERVER_ERROR("Product not found", null);

            var updatedProduct = modelMapper.map(request, Product.class);
            updatedProduct.setCreated_at(getProductById.get().getCreated_at());
            updatedProduct.setUpdated_at(new Date());
            updatedProduct.setCreated_by(getProductById.get().getCreated_by());
            updatedProduct.setUpdated_by(request.getUser_id().toString());
            productRepository.update(updatedProduct);

            var data = modelMapper.map(updatedProduct, ProductResponse.class);
            return responseAPI.OK("Success update data product", data);
        }catch (Exception ex){
            var errMsg = String.format("Error Message : %s with Stacktrace : %s",ex.getMessage(),ex.getStackTrace());
            log.error(String.format("%s" , errMsg));
            return responseAPI.INTERNAL_SERVER_ERROR(errMsg,null);
        }
    }

    public ResponseAPI updateStock(UpdateStockRequest request){
        try {
            log.info(String.format("%s productService.updateStock is called", SERVICE_NAME));
            if (request.getId() == null) return responseAPI.INTERNAL_SERVER_ERROR("[id must not be blank]", null);
            var id = UUID.fromString(request.getId());
            var validate = new ValidationRequest(request).validate();
            if (validate.size() > 0) return responseAPI.BAD_REQUEST(validate.toString(), null);
            var getProductById = productRepository.findById(id);
            if (getProductById.isEmpty()) return responseAPI.INTERNAL_SERVER_ERROR("Product not found", null);
            var currentStock = request.getStock() + getProductById.get().getStock();
            productRepositoryJPA.updateStock(currentStock, request.getUser_id(), new Date(), getProductById.get().getId());
            return responseAPI.OK("Success update data product", null);
        }catch (Exception ex){
            var errMsg = String.format("Error Message : %s with Stacktrace : %s",ex.getMessage(),ex.getStackTrace());
            log.error(String.format("%s" , errMsg));
            return responseAPI.INTERNAL_SERVER_ERROR(errMsg,null);
        }
    }
}
