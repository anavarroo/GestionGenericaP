package com.viewnext.auditservice.services;

import com.viewnext.auditservice.persistence.model.AuditingData;
import com.viewnext.auditservice.persistence.repository.AuditorRepositoryI;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class AuditingServiceImpl implements AuditingServiceI {

    private final AuditorRepositoryI auditorRepo;
    private final EmailServiceImpl emailService;

    @Autowired
    public AuditingServiceImpl(AuditorRepositoryI auditorRepo, EmailServiceImpl emailService) {
        this.auditorRepo = auditorRepo;
        this.emailService = emailService;
    }

    @Override
    public AuditingData saveAudit(AuditingData audit) {
        AuditingData savedAudit = auditorRepo.save(audit);
        // String emailContent = "A new audit entry has been created:\n\n" +
        //         "Author: " + savedAudit.getCreatedBy() + "\n" +
        //         "Date: " + savedAudit.getCreatedDate() + "\n" +
        //         "Endpoint: " + savedAudit.getTypeRequest();
        // emailService.sendEmail("alejandro1052004@gmail.com", "New Audit Entry", emailContent);

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

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);


        try {

            // Quien envia el correo
            message.setFrom(new InternetAddress("correopruebacorreop@gmail.com"));

            // A quien va dirigido
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("correopruebacorreop@gmail.com"));

            message.setSubject("New Audit Entry");
            message.setText("A new audit entry has been created:\n\n" +
                             "Author: " + savedAudit.getCreatedBy() + "\n" +
                             "Date: " + savedAudit.getCreatedDate() + "\n" +
                             "Endpoint: " + savedAudit.getTypeRequest());

            Transport t = session.getTransport();

            t.connect("correopruebacorreop@gmail.com","");

            t.sendMessage(message,message.getAllRecipients());

            t.close();

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }



        return savedAudit;
    }


}
