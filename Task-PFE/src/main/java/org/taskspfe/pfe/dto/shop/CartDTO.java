package org.taskspfe.pfe.dto.shop;


import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartDTO {
    private Long productId;
    private int quantity;
}
