package org.taskspfe.pfe.dto.shop;

import org.taskspfe.pfe.dto.product.ProductDTO;

public record CartItemDTO (
        Long id,
        int quantity,
        ProductDTO product
){
}
