package org.taskspfe.pfe.service.auth;


import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.taskspfe.pfe.dto.auth.LogInResponseDTO;
import org.taskspfe.pfe.dto.auth.LoginDTO;
import org.taskspfe.pfe.dto.auth.RegisterDTO;
import org.taskspfe.pfe.dto.auth.RegisterResponseDTO;
import org.taskspfe.pfe.dto.user.UserEntityDTOMapper;
import org.taskspfe.pfe.model.role.Role;
import org.taskspfe.pfe.model.token.Token;
import org.taskspfe.pfe.model.token.TokenType;
import org.taskspfe.pfe.model.user.UserEntity;
import org.taskspfe.pfe.security.jwt.JWTService;
import org.taskspfe.pfe.service.role.RoleService;
import org.taskspfe.pfe.service.token.TokenService;
import org.taskspfe.pfe.service.user.UserEntityService;
import org.taskspfe.pfe.utility.CustomResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class AuthServiceImpl  implements  AuthService{
    private final AuthenticationManager authenticationManager;
    private final UserEntityService userEntityService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserEntityDTOMapper userEntityDTOMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserEntityService userEntityService, RoleService roleService, TokenService tokenService, PasswordEncoder passwordEncoder, JWTService jwtService, UserEntityDTOMapper userEntityDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.userEntityService = userEntityService;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userEntityDTOMapper = userEntityDTOMapper;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomResponseEntity<RegisterResponseDTO>> register(@NotNull final RegisterDTO registerDto, final String roleName) {
        if (userEntityService.isEmailRegistered(registerDto.getEmail())) {
            throw new IllegalArgumentException("Sorry, that email is already taken. Please choose a different one.");
        }


        Role role = roleService.fetchRoleByName(roleName);

        UserEntity user = UserEntity.builder()
                        .firstName(registerDto.getFirstName())
                        .lastName(registerDto.getLastName())
                        .email(registerDto.getEmail().toLowerCase())
                        .phoneNumber(registerDto.getPhoneNumber())
                        .createdAt(LocalDateTime.now())
                        .password(passwordEncoder.encode(registerDto.getPassword()))
                        .cartItems(new ArrayList<>())
                        .isEnabled(true)
                        .role(role)
                        .build();
        UserEntity savedUser = userEntityService.saveUser(user);


        final RegisterResponseDTO registerResponseDTO = RegisterResponseDTO
                .builder()
                .userEntityDTO(userEntityDTOMapper.apply(savedUser))
                .build();
        final CustomResponseEntity<RegisterResponseDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, registerResponseDTO);
        return new ResponseEntity<>(customResponseEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CustomResponseEntity<LogInResponseDTO>> login(@NotNull final LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userEntityService.getUserEntityByEmail(loginDto.getEmail());

        revokeAllUserAccessTokens(user);
        String jwtAccessToken = revokeGenerateAndSaveToken(user);


        final LogInResponseDTO logInResponseDto = LogInResponseDTO
                .builder()
                .userEntityDTO(userEntityDTOMapper.apply(user))
                .accessToken(jwtAccessToken)
                .build();
        final CustomResponseEntity<LogInResponseDTO> customResponseEntity = new CustomResponseEntity<>(HttpStatus.OK, logInResponseDto);
        return new  ResponseEntity<>(customResponseEntity,HttpStatus.OK);
    }


    private String revokeGenerateAndSaveToken(UserEntity user) {
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserAccessTokens(user);
        saveUserAccessToken(user, jwtToken);

        return jwtToken;
    }

    private void saveUserAccessToken(@NotNull UserEntity userEntity, @NotNull String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    private void revokeAllUserAccessTokens(@NotNull UserEntity userEntity) {
        var validUserTokens = tokenService.fetchAllValidTokenByUserId(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }


}
