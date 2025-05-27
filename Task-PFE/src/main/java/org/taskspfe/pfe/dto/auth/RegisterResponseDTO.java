package org.taskspfe.pfe.dto.auth;

import lombok.Builder;
import lombok.Data;
import org.taskspfe.pfe.dto.user.UserEntityDTO;

@Data
@Builder
public class RegisterResponseDTO {
    private UserEntityDTO userEntityDTO;
}
