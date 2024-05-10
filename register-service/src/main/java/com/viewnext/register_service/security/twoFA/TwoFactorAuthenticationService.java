package com.viewnext.register_service.security.twofa;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

/**
 * Servicio para la autenticación de dos factores (2FA).
 */
@Service
public class TwoFactorAuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(TwoFactorAuthenticationService.class);

    /**
     * Genera un nuevo secreto para la autenticación de dos factores.
     *
     * @return El secreto generado.
     */
    public String generateNewSecret(){
        return new DefaultSecretGenerator().generate();
    }

    /**
     * Genera un URI de imagen QR para el código secreto proporcionado.
     *
     * @param secret El secreto para generar el código QR.
     * @return El URI de la imagen QR.
     */
    public String generateQrCodeImageUri(String secret){
        QrData data = new QrData.Builder()
                .label("Generic 2FA Example")
                .secret(secret)
                .issuer("Generic App")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];
        try{
            imageData = generator.generate(data);
        } catch (QrGenerationException e) {
            e.printStackTrace();
            log.error("Error while generating QR-CODE");
        }

        return getDataUriForImage(imageData,generator.getImageMimeType());
    }

    /**
     * Verifica si el código de una sola vez (OTP) proporcionado es válido.
     *
     * @param secret El secreto para la autenticación de dos factores.
     * @param code   El código de una sola vez proporcionado por el usuario.
     * @return true si el código OTP es válido, false de lo contrario.
     */
    public boolean isOtpValid(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }

    /**
     * Verifica si el código de una sola vez (OTP) proporcionado no es válido.
     *
     * @param secret El secreto para la autenticación de dos factores.
     * @param code   El código de una sola vez proporcionado por el usuario.
     * @return true si el código OTP no es válido, false de lo contrario.
     */
    public boolean isOtpNotValid(String secret, String code) {
        return !this.isOtpValid(secret, code);
    }
}
