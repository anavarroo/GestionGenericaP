package com.viewnext.register_service.security.services;

import com.viewnext.register_service.persistence.dto.UserDto;
import com.viewnext.register_service.persistence.model.Role;
import com.viewnext.register_service.persistence.model.User;
import com.viewnext.register_service.persistence.repository.UserRepositoryI;
import com.viewnext.register_service.security.model.AuthResponse;
import com.viewnext.register_service.security.model.LoginRequest;
import com.viewnext.register_service.security.model.RegisterRequest;
import com.viewnext.register_service.security.model.VerificationRequest;
import com.viewnext.register_service.security.twoFA.TwoFactorAuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepo;

    private final JWTServiceI jwtMngm;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TwoFactorAuthenticationService tfaService;

    @Autowired
    public AuthServiceImpl(UserRepositoryI userRepo, JWTServiceI jwtMngm, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TwoFactorAuthenticationService tfaService) {
        this.userRepo = userRepo;
        this.jwtMngm = jwtMngm;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tfaService = tfaService;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request La solicitud de registro.
     * @return La respuesta de autenticación.
     */
    @Override
    public UserDto register(RegisterRequest request) {
        User user = new User(request.getNombre(),
                request.getApellidos(), request.getEdad(), request.getCorreo(),
                request.getDireccion(), request.getTelefono(),
                passwordEncoder.encode(request.getContrasena()), request.isEstado(),
                Role.USER, request.isMfaEnabled());


        if(request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }
        userRepo.save(user);


        return convertToDto(user);
    }

    /**
     * Inicia sesión en el sistema.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta de autenticación.
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getCorreo(),
                request.getContrasena()));
        User user = userRepo.findByCorreo(request.getCorreo());

        var jwtToken = jwtMngm.getToken(user);

        if (user.isMfaEnabled()){
            return AuthResponse.builder()
                    .token("")
                    .mfaEnabled(true)
                    .build();
        }

        return AuthResponse.builder()
                .token(jwtToken)
                .mfaEnabled(false)
                .build();
    }

    /**
     * Convierte un objeto User en un objeto UserDto.
     *
     * @param user Objeto User a convertir.
     * @return Objeto UserDto convertido.
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setNombre(user.getNombre());
        userDto.setApellidos(user.getApellidos());
        userDto.setCorreo(user.getCorreo());
        userDto.setEdad(user.getEdad());
        userDto.setDireccion(user.getDireccion());
        userDto.setTelefono(user.getTelefono());
        userDto.setMfaEnabled(user.isMfaEnabled());
        return userDto;
    }

    public AuthResponse verifyCode(
            VerificationRequest verificationRequest
    ) {
        User user = userRepo
                .findByCorreo(verificationRequest.getEmail());
        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtMngm.getToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }
}
