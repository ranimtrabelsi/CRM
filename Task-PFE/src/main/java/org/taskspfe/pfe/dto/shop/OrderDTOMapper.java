package org.taskspfe.pfe.dto.shop;


import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.model.shop.Order;

import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getOrderedAt(),
                order.getCartItems().stream().map(new CartItemDTOMapper()).toList(),
                new UserEntityDTOMapper().apply(order.getUser())
        );
    }
}
