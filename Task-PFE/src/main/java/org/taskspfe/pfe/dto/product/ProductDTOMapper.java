package org.taskspfe.pfe.dto.product;

import org.springframework.stereotype.Service;
import org.taskspfe.pfe.model.product.Product;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product , ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getImage()
        );
    }
}
