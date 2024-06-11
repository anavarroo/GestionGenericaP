package com.viewnext.auditservice.services;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl {

    public void sendEmail(String to, String subject, String text) {

        Properties props = getProperties();

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);

        try {

            // Quien envia el correo
            message.setFrom(new InternetAddress("correopruebacorreop@gmail.com"));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);
            message.setText(text);

            Transport t = session.getTransport();

            t.connect("correopruebacorreop@gmail.com","prueba1234");

            t.sendMessage(message,message.getAllRecipients());

            t.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();

        // Nombre del host de correo, es smtp.gmail.com
        props.setProperty("mail.smtp.host", "smtp.gmail.com");

        // TLS si está disponible
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Puerto de gmail para envio de correos
        props.setProperty("mail.smtp.port","587");

        // Nombre del usuario
        props.setProperty("mail.smtp.user", "correopruebacorreop@gmail.com");

        // Si requiere o no usuario y password para conectarse.
        props.setProperty("mail.smtp.auth", "true");
        return props;
    }

}
