/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.service;

import nt.application.mapspace.model.User;
import java.util.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.Authenticator.*;
import javax.mail.PasswordAuthentication.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Elnic
 */
public class EmailServiceImpl implements EmailService {

    @Override
    public void userRegistrationSuccessEmail(User user) {
        System.out.println("Testing Email");
        String d_email = "registration@gnarlitronic.com",
                d_password="BuffaloBeefBoy1989_ix",
                d_host = "localhost",
                d_port = "587",
                m_to = user.getEmail(),
                m_subject = "mapspace registration",
                m_text = "Your account has been successfully created. Username: "+user.getUsername();
        
        
        Properties props = new Properties();
        
            props.put("mail.smtp.user", d_email);
            props.put("mail.smtp.host", d_host);
            props.put("mail.smtp.port", d_port);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.port", d_port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

//            props.put("mail.transport.protocol","smtp");
//            props.put("mail.smtp.starttls.enable","false");
//            props.put("mail.smtp.host","localhost");
//            props.put("mail.smtp.auth","true");
//            props.put("mail.smtp.user","registration@gnarlitronic.com");
//            props.put("mail.smtp.password","BuffaloBeefBoy1989_ix");
//            props.put("mail.smtp.from","registration@gnarlitronic.com");
            
            Session session = Session.getDefaultInstance(props);
            
            try {
                MimeMessage message = new MimeMessage(session);
                
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
                message.setSubject(m_subject);
                message.setContent(m_text, "text/html");
                
                Transport.send(message);
            
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            
             
         
    }
    
}
