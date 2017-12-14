package artintech;

import com.vaadin.server.VaadinSession;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by Анатолий on 09.01.2015.
 * http://www.journaldev.com/2532/java-program-to-send-email-using-smtp-gmail-tls-ssl-attachment-image-example
 */
public class MailerSSL {
    public void sendSimpleMail(String toEmail,String themes, String bodyMessage) throws Exception {//TODO:правильные Exception
        /**
         Outgoing Mail (SMTP) Server
         requires TLS or SSL: smtp.gmail.com (use authentication)
         Use Authentication: Yes
         Port for SSL: 465
         */
/*--------------------------------------------------------------------------
        final String fromEmail = "tomat56@mail.ru"; //requires valid gmail id
        final String password = "tomatoma56"; // correct password for gmail id
        final String toEmail = "tomat56@mail.ru"; // can be any email id
        final String themes = "Регистрация на ресурсе СОУТ";
        final String bodyMessage = "Тело собщения";
-----------------------------------------------------------------------------*/
//        System.out.println("SSLEmail Start");

        Properties props;
        String propFileName = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath() + "/../../SETUP/config.properties";
        File fsetup = new File(propFileName);
        String pt = fsetup.getCanonicalPath();
        InputStream inputStream;




        if(fsetup.exists()) {
            inputStream = new FileInputStream(fsetup);
        } else {
            throw new FileNotFoundException("Файл "+ pt + "не найден");
        }
        if (inputStream !=null){
            props = new Properties();
        } else {
            throw new FileNotFoundException("Файл "+ pt + "не найден");
        }
        props.load(inputStream);
        //---------------------------------------
        //props.setProperty("from.name","АртИнТех");
        //--------------------------------------
        final String login = props.getProperty("login");
        final String password =props.getProperty("password");
        final String fromEmail = props.getProperty("from.email");
        final String fromName = props.getProperty("from.name");
/* -----------------------------------------------------------
        props.put("mail.smtp.host", "smtp.mail.ru"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port
--------------------------------------------------------------- */
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        //System.out.println("Session created");
        EmailUtil.sendEmail(session, toEmail,themes, bodyMessage, fromEmail, fromName);

        //EmailUtil.sendAttachmentEmail(session, toEmail,"SSLEmail Testing Subject with Attachment", "SSLEmail Testing Body with Attachment");
        //EmailUtil.sendImageEmail(session, toEmail,"SSLEmail Testing Subject with Image", "SSLEmail Testing Body with Image");
    }

}
