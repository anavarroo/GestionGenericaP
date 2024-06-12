package com.viewnext.register_service.security.services;

import com.viewnext.register_service.exceptions.DatosIncompletos;
import com.viewnext.register_service.exceptions.DatosIncorrectos;
import com.viewnext.register_service.exceptions.UsuarioNoEncontrado;
import com.viewnext.register_service.exceptions.UsuarioYaExiste;
import com.viewnext.register_service.persistence.dto.UserDtoRegister;
import com.viewnext.register_service.persistence.model.AuditingData;
import com.viewnext.register_service.persistence.model.ExceptionHandler;
import com.viewnext.register_service.persistence.model.User;
import com.viewnext.register_service.persistence.repository.UserRepositoryI;
import com.viewnext.register_service.published.RabbitMQExceptionProducer;
import com.viewnext.register_service.published.RabbitMQJsonProducer;
import com.viewnext.register_service.security.model.AuthResponse;
import com.viewnext.register_service.security.model.LoginRequest;
import com.viewnext.register_service.security.model.RegisterRequest;
import com.viewnext.register_service.security.model.VerificationRequest;
import com.viewnext.register_service.security.twofa.TwoFactorAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Implementación del servicio de autenticación.
 */
@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepo;

    private final JWTServiceI jwtMngm;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TwoFactorAuthenticationService tfaService;

    private final RabbitMQJsonProducer rabbitMQProducer;

    private final RabbitMQExceptionProducer rabbitMQExceptionProducer;

    @Autowired
    public AuthServiceImpl(UserRepositoryI userRepo, JWTServiceI jwtMngm,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           TwoFactorAuthenticationService tfaService,
                           RabbitMQJsonProducer rabbitMQProducer,
                           RabbitMQExceptionProducer rabbitMQExceptionProducer) {
        this.userRepo = userRepo;
        this.jwtMngm = jwtMngm;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tfaService = tfaService;
        this.rabbitMQProducer = rabbitMQProducer;
        this.rabbitMQExceptionProducer = rabbitMQExceptionProducer;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request La solicitud de registro.
     * @return La respuesta de autenticación.
     */
    @Override
    public UserDtoRegister register(RegisterRequest request) {

        if (request.getNombre().isEmpty() || request.getNombre().isBlank()
        || request.getApellidos().isEmpty() || request.getApellidos().isBlank()
        || request.getCorreo().isEmpty() || request.getCorreo().isBlank()) {

            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(request.getCorreo());
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("auth/register");
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (userRepo.existsByCorreo(request.getCorreo())) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(request.getCorreo());
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("auth/register");
            exceptionHandler.setMessage("El usuario ya existe");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioYaExiste("El usuario ya existe");
        }

        User user = new User(request.getNombre(),
                request.getApellidos(), request.getCorreo(),
                passwordEncoder.encode(request.getContrasena()),
                request.isMfaEnabled());


        if(request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }


        userRepo.save(user);

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(request.getCorreo());
        auditingData.setTypeRequest("/auth/register");

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

        return convertToDtoRegister(user);
    }

    /**
     * Inicia sesión en el sistema.
     *
     * @param request La solicitud de inicio de sesión.
     * @return La respuesta de autenticación.
     */
    @Override
    public AuthResponse login(LoginRequest request) {

        if (request.getCorreo().isBlank() || request.getCorreo().isEmpty()
            || request.getContrasena().isBlank() || request.getContrasena().isEmpty()) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(request.getCorreo());
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("auth/login");
            exceptionHandler.setMessage("Los datos no pueden estar vacios");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncompletos("Los datos no pueden estar vacios");
        }

        if (!userRepo.existsByCorreo(request.getCorreo())) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(request.getCorreo());
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("auth/login");
            exceptionHandler.setMessage("El usuario no esta registrado");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new UsuarioNoEncontrado("El usuario no esta registrado");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getCorreo(),
                request.getContrasena()));

        User user = userRepo.findByCorreo(request.getCorreo());

        if (!passwordEncoder.matches(request.getContrasena(), user.getPassword())) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();

            exceptionHandler.setCreatedBy(request.getCorreo());
            exceptionHandler.setCreatedDate(LocalDate.now());
            exceptionHandler.setTypeRequest("auth/login");
            exceptionHandler.setMessage("La contraseña o el usuario son incorrecta");

            rabbitMQExceptionProducer.sendJsonMessage(exceptionHandler.toString());

            throw new DatosIncorrectos("La contraseña o el usuario son incorrecta");
        }

        var jwtToken = jwtMngm.getToken(user);

        String secret = user.getSecret();


        if (user.isMfaEnabled()){
            return AuthResponse.builder()
                    .token(jwtToken)
                    .mfaEnabled(true)
                    .secretImageUri(secret)
                    .build();

        }

        AuditingData auditingData = new AuditingData();
        auditingData.setCreatedBy(request.getCorreo());
        auditingData.setTypeRequest("/auth/login");

        String data = auditingData.toString();

        rabbitMQProducer.sendJsonMessage(data);

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
    private UserDtoRegister convertToDtoRegister(User user) {
        UserDtoRegister userDto = new UserDtoRegister();
        userDto.setNombre(user.getNombre());
        userDto.setApellidos(user.getApellidos());
        userDto.setCorreo(user.getCorreo());
        userDto.setMfaEnabled(user.isMfaEnabled());
        userDto.setSecret(user.getSecret());
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
