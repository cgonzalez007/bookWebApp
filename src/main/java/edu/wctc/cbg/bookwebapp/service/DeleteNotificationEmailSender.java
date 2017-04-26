package edu.wctc.cbg.bookwebapp.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author jlombardo
 */
@Service("deleteNoticeEmailSender")
public class DeleteNotificationEmailSender {
    @Autowired
    private transient MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    public void sendEmail(Object data) throws MailException {
        String recordType = (String)data;
        
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setSubject("bookWebApp - Deleted " + recordType + " Record");
        
        // set the messsage
        msg.setText(" A " + recordType + " record was deleted on : " + new Date());         
        
        try {
            mailSender.send(msg);
        } catch(NullPointerException ex) {
            throw new MailSendException(ex.getMessage());
        }
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}

   