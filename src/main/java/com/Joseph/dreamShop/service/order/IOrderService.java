package com.Joseph.dreamShop.service.order;

import com.Joseph.dreamShop.dto.OrderDto;
import com.Joseph.dreamShop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
