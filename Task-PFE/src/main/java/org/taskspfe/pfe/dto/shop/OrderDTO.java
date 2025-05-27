package org.taskspfe.pfe.dto.shop;

import org.taskspfe.pfe.dto.user.UserEntityDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO (
        Long id,
        double totalPrice,
        LocalDateTime orderedAt,
        List<CartItemDTO> cartItems,
        UserEntityDTO user
){
}
