package com.viewnext.mail_server;

import jakarta.mail.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class MailServerApplication {

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setProperty("un nombre", "un valor");
		p.setProperty("PI", "3.1416");

		Properties prop = new Properties();

		// Deshabilitamos TLS
		prop.setProperty("mail.pop3.starttls.enable", "false");

		// Hay que usar SSL
		prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
		prop.setProperty("mail.pop3.socketFactory.fallback", "false");

		// Puerto 995 para conectarse.
		prop.setProperty("mail.pop3.port","995");
		prop.setProperty("mail.pop3.socketFactory.port", "995");

		Session sesion = Session.getInstance(prop);
		sesion.setDebug(true);
		try {
        	Store store = null;
            store = sesion.getStore("pop3");
			store.connect("pop.gmail.com","ejemplo@gmail.com","la password ");
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

			Message [] mensajes = folder.getMessages();

			for (int i=0;i<mensajes.length;i++)
			{
				System.out.println("From:"+mensajes[i].getFrom()[0].toString());
				System.out.println("Subject:"+mensajes[i].getSubject());
			}

			for (int i=0;i<mensajes.length;i++)
			{
				System.out.println(mensajes[i].getContent());
			}

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }


        SpringApplication.run(MailServerApplication.class, args);
	}

}
