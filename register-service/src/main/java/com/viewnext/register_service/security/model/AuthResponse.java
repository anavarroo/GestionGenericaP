package com.viewnext.register_service.security.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonProperty;
=======
import com.fasterxml.jackson.annotation.JsonInclude;
>>>>>>> origin/feature-alberto
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

<<<<<<< HEAD
    private String accessToken;
    private String refreshToken;
    private boolean mfaEnable;
=======
    /** El token de autenticación generado. */
    private String token;

    private boolean mfaEnabled;

    private String secretImageUri;
>>>>>>> origin/feature-alberto
}
