package com.viewnext.register_service.security.model;

import lombok.Builder;
import lombok.Data;

/**
 * Representa una solicitud de verificación.
 */
@Data
@Builder
public class VerificationRequest {

    /** El correo electrónico para la verificación. */
    private String email;

    /** El código de verificación. */
    private String code;
}
