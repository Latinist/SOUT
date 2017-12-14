package artintech.spam;

import java.net.URI;

import com.google.gwt.thirdparty.guava.common.net.MediaType;
import com.vaadin.external.org.slf4j.LoggerFactory;
//import uri.sbrf.mail.Attachment;
//import uri.sbrf.mail.ComposedMessage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Анатолий on 08.01.2015.
 */
public class Mail {
/*
     private static final Logger logger = LoggerFactory.getLogger(SendMailImpl.class);

     @Override
     public void sendMessage(final MailPropertyResourceBundle parameters, ComposedMessage mail) throws MessagingException {
            Properties props = new Properties(parameters.getConfiguration());
            Session session = Session.getInstance(props);
            if (parameters.getMailSmtpAuth()) {
                session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(parameters.getMailUser(), parameters.getMailPassword());
                    }
                });
            }

            MimeMessage mimeMessage = new MimeMessage(session);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeMail = new MimeBodyPart();
            if (mail.getContentType() == null) {
                mail.setContentType(MediaType.PLAIN_TEXT_UTF_8);
            }
            mimeMail.setContent(mail.getBody(), mail.getContentType().toString());
            multipart.addBodyPart(mimeMail);
            if (mail.getAttachment() != null) {
                for (Attachment attach : mail.getAttachment()) {
                    MimeBodyPart part = new MimeBodyPart();
                    DataSource ds = new ByteArrayDataSource(attach.getContent(), attach.getContentType().toString());
                    part.setContentID(attach.getId());
                    part.setFileName(attach.getName());
                    part.setDataHandler(new DataHandler(ds));
                    multipart.addBodyPart(part);
                }
            }

            // Тема и контент
            mimeMessage.setSubject(mail.getSubject(), "UTF-8");
            mimeMessage.setContent(multipart);

            // Куда, кому, от кого
            mimeMessage.setFrom(mail.getFrom());
            mimeMessage.setReplyTo(mail.getReplyTo());
            mimeMessage.setRecipients(Message.RecipientType.TO, mail.getTo());
            mimeMessage.setRecipients(Message.RecipientType.CC, mail.getCc());
            mimeMessage.setRecipients(Message.RecipientType.BCC, mail.getBcc());

            if (logger.isDebugEnabled()) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    mimeMessage.writeTo(bos);
                    logger.debug(bos.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Transport.send(mimeMessage);
        }
*/
}
