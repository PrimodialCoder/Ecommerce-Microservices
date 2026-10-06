package com.ecommerce.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseModel {

    private String orderNumber;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus orderStatus;

}
