package org.taskspfe.pfe.dto.material;

import org.taskspfe.pfe.dto.user.UserEntityDTO;

public record MaterialDTO(
        long id,
        String name,
        String description,
        UserEntityDTO client
) {
}
