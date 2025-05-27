package org.taskspfe.pfe.service.auth;


import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.taskspfe.pfe.dto.auth.LogInResponseDTO;
import org.taskspfe.pfe.dto.auth.LoginDTO;
import org.taskspfe.pfe.dto.auth.RegisterDTO;
import org.taskspfe.pfe.dto.auth.RegisterResponseDTO;
import org.taskspfe.pfe.utility.CustomResponseEntity;

public interface AuthService {


    public ResponseEntity<CustomResponseEntity<RegisterResponseDTO>> register(@NotNull final RegisterDTO registerDto , final String roleName);
    public ResponseEntity<CustomResponseEntity<LogInResponseDTO>>  login(@NotNull final LoginDTO loginDto);

}
