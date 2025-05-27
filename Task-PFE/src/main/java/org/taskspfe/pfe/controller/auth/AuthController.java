package org.taskspfe.pfe.controller.auth;



import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskspfe.pfe.dto.auth.LogInResponseDTO;
import org.taskspfe.pfe.dto.auth.LoginDTO;
import org.taskspfe.pfe.dto.auth.RegisterDTO;
import org.taskspfe.pfe.dto.auth.RegisterResponseDTO;
import org.taskspfe.pfe.service.auth.AuthService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController (AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/admin/register")
    public ResponseEntity<CustomResponseEntity<RegisterResponseDTO>> adminRegister(
            @Valid @RequestBody RegisterDTO registerDto,
            @RequestParam(name = "roleName", required = true) final String roleName
            )
    {
        return authService.register(registerDto, roleName);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomResponseEntity<RegisterResponseDTO>> register(
            @Valid @RequestBody RegisterDTO registerDto
    ) {
        return authService.register(registerDto, "CLIENT");
    }


    @PostMapping("/login")
    public ResponseEntity<CustomResponseEntity<LogInResponseDTO>>  login(@Valid @RequestBody LoginDTO loginDto)
    {
        return authService.login(loginDto);
    }



}