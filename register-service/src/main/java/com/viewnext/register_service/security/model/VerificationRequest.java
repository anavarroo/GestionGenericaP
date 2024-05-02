package com.viewnext.register_service.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRequest {

    private String email;
    private String code;
}