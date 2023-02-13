package com.procurement.apps.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateStockRequest {
    private String id;
    @Min(value = 1, message = "stock minimum 1")
    private int stock;
    private String user_id;
}
