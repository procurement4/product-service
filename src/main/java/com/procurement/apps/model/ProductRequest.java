package com.procurement.apps.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    String id;
    @NotBlank(message = "name must be not blank")
    String name;
    @NotBlank(message = "category must be not blank")
    String category;
    @Min(value = 1, message = "stock minimum 1")
    int stock;
    @NotBlank(message = "user_id must be not blank")
    String user_id;
    Boolean is_deleted = false;
}
