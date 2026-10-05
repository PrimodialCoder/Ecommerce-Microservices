package com.ecommerce.inventory.mapper;

import com.ecommerce.inventory.dto.InventoryRequestDto;
import com.ecommerce.inventory.dto.InventoryResponseDto;
import com.ecommerce.inventory.model.Inventory;

public class InventoryMapper {

    public static Inventory toEntity(InventoryRequestDto inventoryRequestDto) {
        return Inventory.builder()
                .skuCode(inventoryRequestDto.getSkuCode())
                .quantity(inventoryRequestDto.getQuantity())
                .build();
    }

    public static InventoryResponseDto toResponseDto(Inventory inventory) {
        return InventoryResponseDto.builder()
                .skuCode(inventory.getSkuCode())
                .inStock(inventory.getQuantity() > 0)
                .availableQuantity(inventory.getQuantity())
                .build();
    }
}
