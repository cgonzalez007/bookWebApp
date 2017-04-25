package edu.wctc.cbg.bookwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author cgonz
 */
@Service("registrationConfirmationEmailSender")
public class RegistrationConfirmationEmailSender {
    @Autowired
    private transient MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;
    
    public void sendEmail(Object data){
        String recordType = (String)data;
    
    }
}
