package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequestDto;
import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderStatus;

public class OrderMapper {
    public static Order toEntity(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setSkuCode(orderRequestDto.getSkuCode());
        order.setQuantity(orderRequestDto.getQuantity());
        order.setPrice(orderRequestDto.getPrice());
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }

    public static OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto(order.getOrderNumber(), order.getOrderStatus());
        return orderResponseDto;
    }
}
