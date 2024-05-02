package com.viewnext.register_service.security.model;

import com.viewnext.register_service.persistence.model.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /** El nombre proporcionado en la solicitud de registro **/
    private String nombre;

    /** Los apellidos proporcionados en la solicitud de registro **/
    private String apellidos;

    /** El correo electrónico proporcionado en la solicitud de registro. */
    private String correo;

    /** La contraseña proporcionada en la solicitud de registro. */
    private String contrasena;

    private Role role;

    private boolean mfaEnable;
}
