package org.taskspfe.pfe.dto.product;

import org.taskspfe.pfe.model.file.FileData;

public record ProductDTO (
        long id,
        String name,
        String description,
        double price,
        int quantity,
        FileData image
){
}
