package org.taskspfe.pfe.dto.auth;


import lombok.Builder;
import lombok.Data;
import org.taskspfe.pfe.dto.user.UserEntityDTO;

@Data
@Builder
public class LogInResponseDTO {
    private UserEntityDTO userEntityDTO;
    private String accessToken;
}
