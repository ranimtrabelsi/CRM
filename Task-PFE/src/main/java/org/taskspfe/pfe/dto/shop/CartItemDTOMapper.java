package org.taskspfe.pfe.dto.shop;

import org.springframework.stereotype.Service;
import org.taskspfe.pfe.dto.product.ProductDTOMapper;
import org.taskspfe.pfe.model.shop.CartItem;

import java.util.function.Function;

@Service
public class CartItemDTOMapper implements Function<CartItem, CartItemDTO> {
    @Override
    public CartItemDTO apply(CartItem cartItem) {
        return new CartItemDTO(
                cartItem.getId(),
                cartItem.getQuantity(),
                new ProductDTOMapper().apply(cartItem.getProduct())
        );
    }
}
