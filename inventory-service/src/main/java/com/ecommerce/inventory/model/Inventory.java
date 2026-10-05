package com.ecommerce.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "inventory")
public class Inventory extends BaseModel {
    @Column(unique = true, nullable = false)
    private String skuCode; //Stock Keeping Unit
    @Column(nullable = false)
    private Integer quantity;
}
