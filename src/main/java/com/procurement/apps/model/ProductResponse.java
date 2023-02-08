package com.procurement.apps.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID id;
    private String name;
    private String category;
    private int stock;
    private Boolean is_deleted;
    private String created_at;
    private Date updated_at;
    private String created_by;
    private String updated_by;
}
