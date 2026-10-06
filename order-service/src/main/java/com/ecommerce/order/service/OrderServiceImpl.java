package com.ecommerce.order.service;

import com.ecommerce.order.client.InventoryFeignClient;
import com.ecommerce.order.dto.InventoryResponseDto;
import com.ecommerce.order.dto.OrderRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final InventoryFeignClient inventoryFeignClient;


    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {

        InventoryResponseDto inventoryResponseDto = inventoryFeignClient.isInStock(orderRequestDto.getSkuCode());
        if(!inventoryResponseDto.isInStock()) {
            throw new RuntimeException("Product is out of stock");
        }
        Order existingOrder = OrderMapper.toEntity(orderRequestDto);
        existingOrder.setOrderNumber(UUID.randomUUID().toString());
        existingOrder.setOrderStatus(OrderStatus.PLACED);
        orderRepository.save(existingOrder);
        return OrderMapper.toResponseDto(existingOrder);

    }
}
