package org.taskspfe.pfe.dto.user;


import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.shop.CartItemDTOMapper;
import org.taskspfe.pfe.model.user.UserEntity;

import java.util.function.Function;

@Service
public class UserEntityDTOMapper implements Function<UserEntity, UserEntityDTO> {
    @Override
    public UserEntityDTO apply(UserEntity userEntity) {
        return new UserEntityDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getCreatedAt(),
                userEntity.isEnabled(),
                userEntity.getRole(),
                userEntity.getCartItems().stream().map(new CartItemDTOMapper()).toList()
        );
    }
}
