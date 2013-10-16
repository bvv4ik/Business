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
import javax.mail.search.FlagTerm; 
import java.io.*; 
import java.text.SimpleDateFormat; 
import java.util.ArrayList; 
import java.util.LinkedList; 
import java.util.Properties; 
 
public class MailRecive { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, IOException { 
        
               String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.gmail.com"; 
        String address="serg_geka@mail.ru";  // 380684053122@sms.beeline.ua
        String login="sergey.belyavtsev"; 
        String password="Serg1313serg13"; 
        String smtpPort="587";  
        
        String pop3Host="pop.gmail.com"; 
        receiveMessage(login, password, pop3Host); 
    } 
 
    public static void receiveMessage(String user, String password, String pop3Host) throws MessagingException, IOException { 
        Authenticator auth = new MyAuthenticator2(user, password); 
 
        Properties props = System.getProperties(); 
        props.put("mail.user", user); 
   //     props.put("mail.host", pop3Host); 
    //    props.put("mail.pop3.port", 995);
      //  props.put("mail.debug", "true"); 
      //  props.put("mail.store.protocol", "pop3"); 
        //props.put("mail.transport.protocol", "smtp"); 
        
    props.put("mail.host", "pop.gmail.com");
    props.put("mail.store.protocol", "pop3s");
    props.put("mail.pop3s.auth", "true");
    props.put("mail.pop3s.port", "995");
        props.put("mail.debug", "true"); 

        
// props.put("mail.debug", "false");
//props.put("mail.pop3.host", pop3Host);
//props.put("mail.pop3.port", 995);
//props.put("mail.pop3.user", user);
//props.put("mail.pop3.timeout", "158000");
//props.put("mail.pop3.connectiontimeout", "158000");

        Session session = Session.getDefaultInstance(props, auth); 
        Store store = session.getStore(); 
        store.connect(); 
        Folder inbox = store.getFolder("INBOX"); 
        inbox.open(Folder.READ_WRITE); 
 
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)); 
        ArrayList<String> attachments = new ArrayList<String>(); 
 
        LinkedList<MailMessageBean> listMessages = getPart(messages, attachments); 
         
        
        
        inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true); 
        inbox.close(false); 
        store.close(); 
    } 
 
    private static LinkedList<MailMessageBean> getPart(Message[] messages, ArrayList<String> attachments) throws MessagingException, IOException { 
        LinkedList<MailMessageBean> listMessages = new LinkedList<MailMessageBean>(); 
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); 
        for (Message inMessage : messages) { 
            attachments.clear(); 
            if (inMessage.isMimeType("text/plain")) { 
                MailMessageBean message = new MailMessageBean(inMessage.getMessageNumber(), MimeUtility.decodeText(inMessage.getSubject()), inMessage.getFrom()[0].toString(), null, f.format(inMessage.getSentDate()), (String) inMessage.getContent(), false, null); 
                listMessages.add(message); 
            } else if (inMessage.isMimeType("multipart/*")) { 
                Multipart mp = (Multipart) inMessage.getContent(); 
                MailMessageBean message = null; 
                for (int i = 0; i < mp.getCount(); i++) { 
                    Part part = mp.getBodyPart(i); 
                    if ((part.getFileName() == null || part.getFileName() == "") && part.isMimeType("text/plain")) { 
                        message = new MailMessageBean(inMessage.getMessageNumber(), inMessage.getSubject(), inMessage.getFrom()[0].toString(), null, f.format(inMessage.getSentDate()), (String) part.getContent(), false, null); 
                    } else if (part.getFileName() != null || part.getFileName() != ""){ 
                        if ((part.getDisposition() != null) && (part.getDisposition().equals(Part.ATTACHMENT))) { 
                            attachments.add(saveFile(MimeUtility.decodeText(part.getFileName()), part.getInputStream())); 
                            if (message != null) message.setAttachments(attachments); 
                        } 
                    } 
                } 
                listMessages.add(message); 
            } 
        } 
        return listMessages; 
    } 
 
    
    
    private static String saveFile(String filename, InputStream input) { 
        String path = "c:\\mail\\"+filename; //attachments
        try { 
            byte[] attachment = new byte[input.available()]; 
            input.read(attachment); 
            File file = new File(path); 
            FileOutputStream out = new FileOutputStream(file); 
            out.write(attachment); 
            input.close(); 
            out.close(); 
            return path; 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return path; 
    } 
} 
 
class MyAuthenticator2 extends Authenticator { 
    private String user; 
    private String password; 
 
    MyAuthenticator2(String user, String password) { 
        this.user = user; 
        this.password = password; 
    } 
 
    public PasswordAuthentication getPasswordAuthentication() { 
        String user = this.user; 
        String password = this.password; 
        return new PasswordAuthentication(user, password); 
    } 
} 
 


 