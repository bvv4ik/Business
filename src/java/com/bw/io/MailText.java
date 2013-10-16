package com.bw.io;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.mail.*; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
import java.io.UnsupportedEncodingException; 
import java.util.Properties; 
 
 
public class MailText { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
        String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.gmail.com"; 
        String address="serg_geka@mail.ru";  // 380684053122@sms.beeline.ua
        String login="sergey.belyavtsev"; 
        String password="Serg1313serg13"; 
        String smtpPort="587";  
        sendSimpleMessage (login, password, address, address, content, subject, smtpPort, smtpHost);         
    } 
 
    public static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost)  
        throws MessagingException, UnsupportedEncodingException { 
        Authenticator auth = new MyAuthenticator1(login, password); 
 
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", smtpPort); 
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING); 
        Session session = Session.getDefaultInstance(props, auth); 
 
        Message msg = new MimeMessage(session); 
        msg.setFrom(new InternetAddress(from)); 
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
        msg.setSubject(subject); 
        msg.setText(content); 
        Transport.send(msg); 
    } 
} 
class MyAuthenticator1 extends Authenticator { 
    private String user; 
    private String password; 
 
    MyAuthenticator1(String user, String password) { 
        this.user = user; 
        this.password = password; 
    } 
 
    public PasswordAuthentication getPasswordAuthentication() { 
        String user = this.user; 
        String password = this.password; 
        return new PasswordAuthentication(user, password); 
    } 
}