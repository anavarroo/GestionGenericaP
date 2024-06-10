package com.viewnext.auditservice.services;

public interface EmailServiceI {

    void sendEmail(String to, String subject, String text);

}
