package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.InventoryRequestDto;
import com.ecommerce.inventory.dto.InventoryResponseDto;
import com.ecommerce.inventory.exception.InventoryNotFoundException;
import com.ecommerce.inventory.mapper.InventoryMapper;
import com.ecommerce.inventory.model.Inventory;
import com.ecommerce.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryResponseDto checkInventory(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(() -> new InventoryNotFoundException("Inventory not found for SKU: " + skuCode));
        return InventoryMapper.toResponseDto(inventory);
    }

    public void addInventory(InventoryRequestDto inventoryRequestDto) {
        Inventory inventory = InventoryMapper.toEntity(inventoryRequestDto);
        inventoryRepository.save(inventory);
    }



}
