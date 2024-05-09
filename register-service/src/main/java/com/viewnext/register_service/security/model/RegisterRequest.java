package com.viewnext.register_service.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una solicitud de registro enviada por el usuario.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /** El nombre proporcionado en la solicitud de registro. */
    private String nombre;

    /** Los apellidos proporcionados en la solicitud de registro. */
    private String apellidos;

    /** La edad proporcionada en la solicitud de registro. */
    private int edad;

    /** El correo electrónico proporcionado en la solicitud de registro. */
    private String correo;

    /** La dirección proporcionada en la solicitud de registro. */
    private String direccion;

    /** El número de teléfono proporcionado en la solicitud de registro. */
    private int telefono;

    /** La contraseña proporcionada en la solicitud de registro. */
    private String contrasena;

    /** El estado del usuario en la solicitud de registro. */
    private boolean estado;

    /** Indica si la autenticación multifactor está habilitada en la solicitud de registro. */
    private boolean mfaEnabled;

    /** El secreto para la autenticación multifactor proporcionado en la solicitud de registro. */
    private String secret;
}
