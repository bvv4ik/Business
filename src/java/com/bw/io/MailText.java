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
 
 private final String SUBJECT = "Subject"; 
        //private final String CONTENT = "Test"; 
 private final String SMTPHOST = "smtp.gmail.com"; 
 private final String FROM = "serg_geka@mail.ru";
        //private final String ADRESSTO = "serg_geka@mail.ru";
 private final String LOGIN = "businesspgasa";      // businesspgasa@gmail.com
 private final String PASSWORD = "123qweasdzxc12"; 
 private final String SMTPPORT = "587"; 
 private final String TO = ""; 

 
 

    public void sendMail(String adressTo, String contentTxt ) throws MessagingException, UnsupportedEncodingException { 
        Authenticator auth = new MyAuthenticator1(LOGIN, PASSWORD); 
 
        
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", SMTPPORT); 
        props.put("mail.smtp.host", SMTPHOST); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.mime.charset", ENCODING); 
        Session session = Session.getInstance(props, auth); // getDefaultInstance
 
        Message msg = new MimeMessage(session); 
        msg.setFrom(new InternetAddress(FROM)); 
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(adressTo)); 
        msg.setSubject(SUBJECT); 
        msg.setText(contentTxt); 
        Transport.send(msg); 
    }
            
    
    
    
    
       public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
           MailText mt = new MailText();
           mt.sendMail("serg_geka@mail.ru", "Ваша ссылка для входа в PGASA без пароля:  \n\n  http://pgasa-edu-ua.org:8082/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu  \n\n ") ;
           //mt.sendMail("serg_geka@mail.ru", "привет!") ;
     // http://localhost:8080/#
//        String subject = "Subject"; 
//        String content = "Test"; 
//        String smtpHost="smtp.gmail.com"; 
//        String address="serg_geka@mail.ru";  // 380684053122@sms.beeline.ua
//        String login="sergey.belyavtsev";  // 
//        String password="Serg1313serg13"; 
//        String smtpPort="587";     
//        sendSimpleMessage (login, password, address, address, content, subject, smtpPort, smtpHost);         
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