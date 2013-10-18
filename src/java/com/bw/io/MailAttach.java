/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bw.io;

import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.mail.*; 
import javax.mail.internet.*; 
import java.io.UnsupportedEncodingException; 
import java.util.ArrayList; 
import java.util.Properties; 
 
public class MailAttach { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
          String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.gmail.com"; 
        String address="serg_geka@mail.ru";  // 380684053122@sms.beeline.ua
        String login="businesspgasa"; 
        String password="123qweasdzxc12"; 
        String smtpPort="587";  
 
        String attachment = "c:/attach.jpg"; 
        sendMultiMessage(login, password, address, address, content, subject, attachment, smtpPort, smtpHost); 
    } 
 
    public static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost) throws MessagingException, UnsupportedEncodingException { 
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", smtpPort); 
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING); 
 
        Authenticator auth = new MyAuthenticator(login, password); 
        Session session = Session.getDefaultInstance(props, auth); 
 
        Message msg = new MimeMessage(session); 
        msg.setFrom(new InternetAddress(from)); 
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
        msg.setSubject(subject); 
        msg.setText(content); 
        Transport.send(msg); 
    } 
 
    public static void sendMultiMessage(String login, String password, String from, String to, String content, String subject, String attachment, String smtpPort, String smtpHost) throws MessagingException, UnsupportedEncodingException { 
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", smtpPort); 
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING); 
 
        Authenticator auth = new MyAuthenticator(login, password); 
        Session session = Session.getDefaultInstance(props, auth); 
 
        MimeMessage msg = new MimeMessage(session); 
 
        msg.setFrom(new InternetAddress(from)); 
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
        msg.setSubject(subject, ENCODING); 
 
        BodyPart messageBodyPart = new MimeBodyPart(); 
        messageBodyPart.setContent(content, "text/plain; charset=" + ENCODING + ""); 
        Multipart multipart = new MimeMultipart(); 
        multipart.addBodyPart(messageBodyPart); 
 
        MimeBodyPart attachmentBodyPart = new MimeBodyPart(); 
        DataSource source = new FileDataSource(attachment); 
        attachmentBodyPart.setDataHandler(new DataHandler(source)); 
        attachmentBodyPart.setFileName(MimeUtility.encodeText(source.getName())); 
        multipart.addBodyPart(attachmentBodyPart); 
 
        msg.setContent(multipart); 
 
        Transport.send(msg); 
    } 
} 
 
class MyAuthenticator extends Authenticator { 
    private String user; 
    private String password; 
 
    MyAuthenticator(String user, String password) { 
        this.user = user; 
        this.password = password; 
    } 
 
    public PasswordAuthentication getPasswordAuthentication() { 
        String user = this.user; 
        String password = this.password; 
        return new PasswordAuthentication(user, password); 
    } 
} 