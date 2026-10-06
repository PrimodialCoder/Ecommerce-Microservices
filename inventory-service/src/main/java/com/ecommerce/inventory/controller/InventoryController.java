package com.ecommerce.inventory.controller;

import com.ecommerce.inventory.dto.InventoryRequestDto;
import com.ecommerce.inventory.dto.InventoryResponseDto;
import com.ecommerce.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryResponseDto> isInStock(@PathVariable String skuCode) {
        InventoryResponseDto inventoryResponseDto = inventoryService.checkInventory(skuCode);
        return ResponseEntity.ok(inventoryResponseDto);
    }

    @PostMapping
    public ResponseEntity<String> addInventory(@RequestBody InventoryRequestDto inventoryRequestDto) {
        inventoryService.addInventory(inventoryRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Inventory added successfully");
    }

}
