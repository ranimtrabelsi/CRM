package org.taskspfe.pfe.dto.user;



import org.taskspfe.pfe.dto.shop.CartItemDTO;
import org.taskspfe.pfe.model.role.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record UserEntityDTO (
            UUID id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            LocalDateTime createdAt,
            boolean isEnabled,
            Role role,
            List<CartItemDTO> cartItems
        )
{


}
