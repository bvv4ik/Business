package business.send;

public class MailHelp {
}

/*
  
 
 Инструкция по работе с почтой
 
http://www.quizful.net/post/java-mail-api


JavaMail API – это свободно распространяемая библиотека, с помощью которой приложение может получать доступ к почтовым серверам, позволяя создавать, отправлять и получать электронные письма, в том числе и с вложениями, а также удалять письма с сервера.

Данная библиотека поддерживает следующие протоколы: 

SMTP, сокращение от Simple Mail Transfer Protocol, протокол, который определяет механизм для доставки электронной почты. Любой клиент электронной почты, при передаче почты, соединяется с сервером SMTP, который, в свою очередь, доставляет сообщение на сервер SMTP получателя. А затем получатель извлекает сообщение с помощью POP или IMAP.
POP, сокращение от Post Office Protocol. В настоящее время его третья версия известна как POP3. Большинство почтовых серверов используют этот протокол, чтобы обеспечить получение сообщений пользователем. В действительности POP3 гарантирует лишь, что каждый пользователь имеет свой собственный почтовый ящик.
IMAP, сокращение от Internet Message Access Protocol. Представляет собой более продвинутый протокол для получения сообщений. При использовании IMAP, почтовый сервер должен поддерживать этот протокол. IMAP предоставляет клиентскому приложению доступ к удаленным хранилищам сообщений, как если бы они были локальные. По сравнению с POP, IMAP значительно сильнее нагружает сервер.
Для понимания принципов использования библиотеки JavaMail API необходимо определить несколько ключевых понятий:
Message - абстрактный класс, который представляет собой сообщения электронной почты. JavaMail реализует RFC822 и MIME стандарты обмена сообщениями. MimeMessage класс расширяет Message для работы с сообщениями, имеющими MIME-тип. 
Структура сообщения:

Простое сообщение имеет один объект контента:
 image

Multipart сообщение представляет собой контейнер объектов BodyPart. Структура BodyPart объекта похожа на структуру Message объекта. Каждый объект BodyPart содержит атрибуты и содержимое, но атрибуты Bodypart объекта ограничиваются теми, которые определены в интерфейсе Part.  Содержимое BodyPart объекта – это DataHandler, который содержит либо данные или другой  Multipart объект.
image
Store - абстрактный класс, который представляет собой хранилище сообщений поддерживаемых почтовым сервером и сгруппированных по владельцу. Store использует отдельный протокол доступа. 
Folder - абстрактный класс, который предоставляет возможность иерархически организовывать сообщения. Папки могут содержать сообщения и другие папки. Почтовый сервер по умолчанию предоставляет каждому пользователю папку, а пользователи обычно, создают и заполняют вложенные подпапки. 
Transport - абстрактный класс, который представляет собой спецификацию протокола передачи. Transport использует объект конкретного протокола передачи, чтобы отправить сообщение.
Session - класс, который определяет основные сессии почты. Чтобы передать значения в объект сессии, могут быть использованы Properties. 
Authenticator – класс, который обеспечивает доступ к защищенным ресурсам с помощью имени пользователя и пароля. Ресурсами, может быть что угодно, начиная от простых файлов на серверах. Для JavaMail, ресурс – это сервер. Приложения используют этот класс при получении сессии. Когда требуется авторизация, система будет вызывать метод подкласса (например, getPasswordAuthentication). Этот метод подкласса может делать запросы аутентификации.

Ну а теперь пришло время посмотреть, как все это работает. 
Скачиваем библиотеку тут и импортируем ее в проект: 
http://www.oracle.com/technetwork/java/index-138643.html

Теперь посмотрим, как с помощью JavaMail API можно отправлять простые сообщения:

package ru.quizful; 
 
import javax.mail.*; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
import java.io.UnsupportedEncodingException; 
import java.util.Properties; 
 
public class TestMail { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
        String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.rambler.ru"; 
        String address="test@rambler.ru"; 
        String login="test"; 
        String password="test"; 
        String smtpPort="25";  
        sendSimpleMessage (login, password, address, address, content, subject, smtpPort, smtpHost);         
    } 
 
    public static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost)  
        throws MessagingException, UnsupportedEncodingException { 
        Authenticator auth = new MyAuthenticator(login, password); 
 
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", smtpPort); 
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.auth", "true"); 
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
Итак, сначала нам необходимо узнать параметры почтового сервера с которого мы будем отправлять письмо, а именно надо узнать хост SMTP протокола и его порт (обычно это 25 порт). Далее указываем свои существующие данные для авторизации на сервере, а также адрес получателя (в моем примере я для простоты отправлял письмо самому себе).  
Для отправки письма нам необходимо:

получить текущую сессию
создать новое сообщение
установить поля сообщения
отправить его
Для получения сессии используется метод Session.getDefaultInstance(Properties props, Authenticator authenticator) который возвращает объект сессии по умолчанию. Если по умолчанию он до сих пор не установлен, то новый объект сессии будет создан и установлен как объект по умолчанию. 

Параметр props – атрибуты новой сессии 

Параметр authenticator – объект Authenticator, который используется для того, чтобы проверить права доступа пользователя.

После получения сессии можно создать новое сообщение и задать его поля. Далее все просто – отправляем наше сообщение с помощью статического метода класса Transport.

Теперь попробуем отправить Multipart сообщение, а именно письмо с вложением. Вот вариант реализации:

package ru.quizful; 
 
import javax.activation.DataHandler; 
import javax.activation.DataSource; 
import javax.activation.FileDataSource; 
import javax.mail.*; 
import javax.mail.internet.*; 
import java.io.UnsupportedEncodingException; 
import java.util.ArrayList; 
import java.util.Properties; 
 
public class TestMail { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, UnsupportedEncodingException { 
        String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.rambler.ru"; 
        String address="test@rambler.ru"; 
        String login="test"; 
        String password="test"; 
        String smtpPort="25"; 
 
        String attachment = "c:/attach.jpg"; 
        sendMultiMessage(login, password, address, address, content, subject, attachment, smtpPort, smtpHost); 
    } 
 
    public static void sendSimpleMessage(String login, String password, String from, String to, String content, String subject, String smtpPort, String smtpHost) throws MessagingException, UnsupportedEncodingException { 
        Properties props = System.getProperties(); 
        props.put("mail.smtp.port", smtpPort); 
        props.put("mail.smtp.host", smtpHost); 
        props.put("mail.smtp.auth", "true"); 
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
Для отправки письма с вложением необходимо:

получить текущую сессию
создать новое сообщение
установить основные поля сообщения
добавить в сообщение часть с текстом письма
добавить в сообщение часть с вложением
отправить сообщение
Итак, ищем отличия. Во-первых, в методе main появился еще один параметр – attachment, содержащий путь к файлу который будет во вложении. Во-вторых, изменилось формирование письма, изучим его подробнее.
Также как и в предыдущем примере сначала мы получаем текущую сессию и создаем экземпляр MimeMessage, затем указываем основные параметры письма. Но теперь письмо состоит из двух частей: 

Первая часть содержит в себе контент – т.е содержимое письма (его текст). При этом для правильного отображения необходимо указать тип контента и его кодировку во избежание получения непонятных кракозябр в письме. Первая часть добавляется к сообщению.
Вторая часть должна содержать в себе вложение. Для этого мы получаем источник данных (DataSource) нужного файла и устанавливаем его в качестве DataHandler, также указываем имя файла. MimeUtility использована для ликвидации все тех же проблем с кодировкой. Добавляем вторую часть к сообщению.
Отправляем готовое сообщение все тем же незамысловатым методом.

Теперь давайте получим письма которые мы сами себе отправили!
Тут все немного сложнее…

package ru.quizful; 
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
 
public class TestMail { 
    static final String ENCODING = "UTF-8"; 
 
    public static void main(String args[]) throws MessagingException, IOException { 
        String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.rambler.ru"; 
        String address="test@rambler.ru"; 
        String login="test"; 
        String password="test"; 
        String smtpPort="25"; 
        String pop3Host="pop3.rambler.ru"; 
        receiveMessage(login, password, pop3Host); 
    } 
 
    public static void receiveMessage(String user, String password, String host) throws MessagingException, IOException { 
        Authenticator auth = new MyAuthenticator(user, password); 
 
        Properties props = System.getProperties(); 
        props.put("mail.user", user); 
        props.put("mail.host", host); 
        props.put("mail.debug", "false"); 
        props.put("mail.store.protocol", "pop3"); 
        props.put("mail.transport.protocol", "smtp"); 
 
        Session session = Session.getDefaultInstance(props, auth); 
        Store store = session.getStore(); 
        store.connect(); 
        Folder inbox = store.getFolder("INBOX"); 
        inbox.open(Folder.READ_WRITE); 
 
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)); 
        ArrayList<String> attachments = new ArrayList<String>(); 
 
        LinkedList<MessageBean> listMessages = getPart(messages, attachments); 
 
        inbox.setFlags(messages, new Flags(Flags.Flag.SEEN), true); 
        inbox.close(false); 
        store.close(); 
    } 
 
    private static LinkedList<MessageBean> getPart(Message[] messages, ArrayList<string> attachments) throws MessagingException, IOException { 
        LinkedList<MessageBean> listMessages = new LinkedList<MessageBean>(); 
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); 
        for (Message inMessage : messages) { 
            attachments.clear(); 
            if (inMessage.isMimeType("text/plain")) { 
                MessageBean message = new MessageBean(inMessage.getMessageNumber(), MimeUtility.decodeText(inMessage.getSubject()), inMessage.getFrom()[0].toString(), null, f.format(inMessage.getSentDate()), (String) inMessage.getContent(), false, null); 
                listMessages.add(message); 
            } else if (inMessage.isMimeType("multipart/*")) { 
                Multipart mp = (Multipart) inMessage.getContent(); 
                MessageBean message = null; 
                for (int i = 0; i < mp.getCount(); i++) { 
                    Part part = mp.getBodyPart(i); 
                    if ((part.getFileName() == null || part.getFileName() == "") && part.isMimeType("text/plain")) { 
                        message = new MessageBean(inMessage.getMessageNumber(), inMessage.getSubject(), inMessage.getFrom()[0].toString(), null, f.format(inMessage.getSentDate()), (String) part.getContent(), false, null); 
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
        String path = "attachments\\"+filename; 
        try { 
            byte[] attachment = new byte[input.available()]; 
            input.read(attachment); 
            File file = new File(path); 
            FileOutputStream out = new FileOutputStream(file); 
            out.write(attachment); 
            input.close(); 
            out.close(); 
            return path; 
        } catch (IOException oException) { 
            oException.printStackTrace(); 
        } 
        return path; 
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
 
И еще один класс: 
package ru.quizful; 
 
import java.io.Serializable; 
import java.util.ArrayList; 
 
public class MessageBean implements Serializable { 
    private String subject; 
    private String from; 
    private String to; 
    private String dateSent; 
    private String content; 
    private boolean isNew; 
    private int msgId; 
    private ArrayList<String> attachments; 
 
    public MessageBean(int msgId, String subject, String from, String to, String dateSent, String content, boolean isNew, ArrayList<String> attachments) { 
        this.subject = subject; 
        this.from = from; 
        this.to = to; 
        this.dateSent = dateSent; 
        this.content = content; 
        this.isNew = isNew; 
        this.msgId = msgId; 
        this.attachments = attachments; 
    } 
 
    public String getSubject() { 
        return subject; 
    } 
 
    public void setSubject(String subject) { 
        this.subject = subject; 
    } 
 
    public String getFrom() { 
        return from; 
    } 
 
    public void setFrom(String from) { 
        this.from = from; 
    } 
 
    public String getDateSent() { 
        return dateSent; 
    } 
 
    public void setDateSent(String dateSent) { 
        this.dateSent = dateSent; 
    } 
 
    public String getContent() { 
        return content; 
    } 
 
    public void setContent(String content) { 
        this.content = content; 
    } 
 
    public String getTo() { 
        return to; 
    } 
 
    public void setTo(String to) { 
        this.to = to; 
    } 
 
    public boolean isNew() { 
        return isNew; 
    } 
 
    public void setNew(boolean aNew) { 
        isNew = aNew; 
    } 
 
    public int getMsgId() { 
        return msgId; 
    } 
 
    public void setMsgId(int msgId) { 
        this.msgId = msgId; 
    } 
 
    public ArrayList<String> getAttachments() { 
        return attachments; 
    } 
 
    public void setAttachments(ArrayList<String> attachments) { 
        this.attachments = new ArrayList<String>(attachments); 
    } 
}
Итак, о том, что тут делается. Для начала определим общую идею как все должно работать в теории и что для этого необходимо:

получаем текущую сессию
получаем доступ к хранилищу сообщений
получаем доступ к папке входящих сообщений в этом хранилище
извлекаем из папки все сообщения и начинаем их обработку, т.е извлечение полей сообщений
закрываем за собой папку и хранилище
Что нового в коде:

Появился класс MessageBean – это бин в который мы будем сохранять данные из сообщения. Это сделано для удобства ибо в результате выполнения данного метода вы получите аккуратную коллекцию входящих сообщений с которыми можете потом делать что хотите.
Появился метод getPart, в котором собственно выполняется обработка полученных сообщений, а именно: проверка типа сообщения, в соответствии с которым потом извлекается его содержимое, также данный метод формирует список экземпляров бина сообщения, который собственно и возвращается главному методу.
Появился метод saveFile который сохраняет вложения из писем в каталог attachments. Тут все просто, так что описывать в подробностях не буду.
Появился новый параметр pop3Host который содержит хост POP3 протокола, для входящей почты.
Приступим к более детальному осмотру кода.
Сначала как всегда мы получаем текущую сессию (обратите внимание что свойства, которые мы передаем внутрь метода getDefaultInstance, изменились). После этого из сессии мы получаем хранилище и подключаемся к нему используя метод connect класса Service. Далее мы можем получить из хранилища папку с входящими сообщениями используя метод getFolder, в данном случае мы получаем папку по ее имени. Этот метод по умолчанию возвращает нам закрытую папку, поэтому мы открываем ее на чтение и запись.
Теперь у нас есть доступ к папке. В данном примере, для того чтобы получить письма из папки я использовал поиск внутри каталога, критерием поиска был флаг письма, показывающий, что оно новое и еще не было прочитано, но на серверах POP3 эта функция не поддерживается (возвращаются вообще все письма), так что для получения писем можно использовать простой метод inbox.getMessages(); Оба этих метода возвращают массив сообщений, который передается внутрь метода getPart(), в котором, как уже было сказано выше выполняется проверка типа сообщения:
- Если это простое сообщение, то создается экземпляр бина, поля которого инициализируется содержимым сообщения
- Если это Multipart сообщение, то мы получаем содержимое письма как Multipart и начинаем просматривать части сообщений:

Если часть содержит простой контент, то он сохраняется в полях бина
Если часть содержит вложение, то выполняется метод saveFile(), который сохраняет вложение, а путь к нему сохраняет в бине.
Ну что ж, осталось только удалить за собой письма с сервера…

public class TestMail { 
    static final String ENCODING = "UTF-8"; 
    public static void main(String args[]) throws MessagingException, IOException { 
        String subject = "Subject"; 
        String content = "Test"; 
        String smtpHost="smtp.rambler.ru"; 
        String address="test@rambler.ru"; 
        String login="test"; 
        String password="test"; 
        String smtpPort="25"; 
        String pop3Host="pop3.rambler.ru"; 
 
        deleteMessage(login, password, pop3Host, 1); 
    } 
 
    public static void deleteMessage(String user, String password, String host, int n) throws MessagingException, IOException { 
        Authenticator auth = new MyAuthenticator(user, password); 
 
        Properties props = System.getProperties(); 
        props.put("mail.user", user); 
        props.put("mail.host", host); 
        props.put("mail.debug", "false"); 
        props.put("mail.store.protocol", "pop3"); 
        props.put("mail.transport.protocol", "smtp"); 
 
        Session session = Session.getDefaultInstance(props, auth); 
        Store store = session.getStore(); 
        store.connect(); 
        Folder inbox = store.getFolder("INBOX"); 
        inbox.open(Folder.READ_WRITE); 
 
        inbox.setFlags(n, n, new Flags(Flags.Flag.DELETED), true); 
        inbox.close(true); 
        store.close(); 
    } 
} 
Что надо сделать для удаления письма:

получаем текущую сессию
получаем доступ к хранилищу сообщений
получаем доступ к папке входящих сообщений в этом хранилище
устанавливаем флаг сообщения DELETED = true
обязательно закрываем за собой папку и хранилище
В общем, то расписывать тут нечего, единственное могу поругаться на неудобную установку флага. Потому что он устанавливается, в данном случае по номеру сообщения, а чтобы его узнать надо снова получить все письма с сервера и посмотреть текущий номер нужного нам сообщения. Эту часть я опустил, так как она вся была описана выше. Номер сообщения можно получить с помощью метода getMessageNumber(). Итак зная номер сообщения мы устанавливаем у него флаг и все…теперь главное не забыть закрыть папку и хранилище, ибо именно в этот момент происходит удаление.

На этом я заканчиваю. Поэкспериментировать с протоколом IMAP увы не получилось, так как негде было тестировать код. Да и подустал я что-то, так что оставляю это вам.

Пользуясь всем этим великолепием мне удалось смастерить некое подобие почтового клиента, изготовленного во славу этого замечательного сайта...И клиент этот даже работает!...по крайней мере у меня. Вот как он выглядит:

 image

Исходники вы можете скачать его по адресу: http://rghost.ru/2460228
Информация по использованию в хелпе.

Исходники всех примеров приведенных выше можно скачать здесь: http://rghost.ru/2461932

Напоследок несколько полезных ссылок:
Официальная документация - http://java.sun.com/products/javamail/javadocs/index.html?overview-summary.html 
Часто задаваемые вопросы (кратко и очень полезно!) - http://www.oracle.com/technetwork/java/faq-135477.html
Отправка почты через протокол SSL/TSL -  http://j2w.blogspot.com/2008/01/java-mail-ssltls.html

Если Вам понравилась статья, проголосуйте за нее


*/