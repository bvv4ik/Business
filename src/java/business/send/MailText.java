package business.send;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import business.Config;
import javax.mail.*; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
import java.io.UnsupportedEncodingException; 
import java.util.Properties; 
import org.apache.log4j.Logger;
 
 
public class MailText { 
    
 static final String ENCODING = "UTF-8"; 
 
 private final String SUBJECT = "Subject"; 
        //private final String CONTENT = "Test"; 
 private final String SMTPHOST = Config.sValue("sMailSMTP");    //"smtp.gmail.com"; 
 private final String FROM = Config.sValue("sMailFrom");        //"serg_geka@mail.ru";
        //private final String ADRESSTO = "serg_geka@mail.ru";
 private final String LOGIN = Config.sValue("sMailLogin");        //"businesspgasa";      // businesspgasa@gmail.com
 private final String PASSWORD = Config.sValue("sMailPassword");    //"123qweasdzxc12"; 
 private final String SMTPPORT = Config.sValue("sMailPortSMTP");    //"587"; 
 private final String TO = ""; 
 private Logger oLog = Logger.getLogger(getClass());
 

    public void sendMail(String sAddressTo, String sBody ) throws MessagingException, UnsupportedEncodingException { 
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
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(sAddressTo)); 
        msg.setSubject(SUBJECT); 
        msg.setText(sBody);
        Transport.send(msg); 
        oLog.info("sAddressTo="+sAddressTo+",sBody="+sBody);
    }
            
    
    
    
    
       public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
           MailText mt = new MailText();
           //"+Config.sValue("sURL") +"
           //mt.sendMail("serg_geka@mail.ru", "Ваша ссылка для входа в PGASA без пароля:  \n\n  http://pgasa-edu-ua.org:8082/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu  \n\n ") ;
           mt.sendMail("serg_geka@mail.ru", "Ваша ссылка для входа в PGASA без пароля:  \n\n  "+Config.sValue("sURL")+"/#sDO=theLoginForCookie&sCookieLogin=31%26eofrrqpcrgkshspqxmkserqihewgaxqeazdrfjmgfuqunpkanu  \n\n ") ;
           //mt.sendMail("serg_geka@mail.ru", "привет!") ;
     // http://localhost:8080/#
//        String subject = "Subject"; 
//        String content = "Test"; 
//        String smtpHost="smtp.gmail.com"; 
//        String address="serg_geka@mail.ru";  // 380684053122@sms.beeline.ua
//        String login="sergey.belyavtsev";  // 
//        String password="Serg131"; 
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