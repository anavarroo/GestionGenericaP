package com.viewnext.register_service.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse {

    /** El token de autenticación generado. */
    private String token;

    private boolean mfaEnabled;

    private String secretImageUri;
}
