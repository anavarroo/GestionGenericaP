package com.viewnext.register_service.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una respuesta de autenticación.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse {

    /** El token de autenticación generado. */
    private String token;

    /** Factor de doble autenticacion **/
    private boolean mfaEnabled;

    /** Clave secreta doble factor de atutenticacion **/
    private String secretImageUri;
}
