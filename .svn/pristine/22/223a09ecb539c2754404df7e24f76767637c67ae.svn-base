package artintech.spam;

//import artintech.domain.MailPropertyes;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * Created by Анатолий on 08.01.2015.
 */
public class Mailer {
//        ResourceBundle m_appResources = null;
        /**
         * Creates a new instance of CmdMail
         */
        public Mailer() { }

        /**
         * @param args the command line arguments
         */
/*
        public static void main(String[] args) {
            // TODO code application logic here
            CmdMail app = new CmdMail();
            app.loadResources("mail.CmdMail");
            try {
                app.sendSimpleMail();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
*/
        /**
         * Простейшая функция посылки письма, позволяющая использовать русские буквы
         * в его тексте и строке темы.
         */
/*
        public void sendSimpleMail(final MailPropertyes mailPropertyes, String email, String body) throws Exception {//TODO:правильные Exception
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.put("mail.smtp.allow8bitmime", "true");
//            props.setProperty("mail.host", m_appResources.getString("mail.smtp.host"));
            props.setProperty("mail.host", mailPropertyes.getHost());
//            props.setProperty("mail.user", m_appResources.getString("mail.user"));
            props.setProperty("mail.user", mailPropertyes.getUser());
            props.setProperty("mail.password", mailPropertyes.getPassword());
            Session mailSession = Session.getDefaultInstance(props, null);//javax.mail.Session



//            if (parameters.getMailSmtpAuth()) {
                mailSession = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailPropertyes.getUser(), mailPropertyes.getPassword());
                    }
                });
//            }


            mailSession.setDebug(true);
//            fetchMessages (mailSession, "INTRAMAIL", "t01335", "bel123");

            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);

//            message.setFrom(new InternetAddress(m_appResources.getString("mail.user")));
            message.setFrom(new InternetAddress(mailPropertyes.getUser()));

            //необходимо для русского языка в "теме" письма (подошло также UTF-8):
            message.setSubject(mailPropertyes.getTheme(), "KOI8-R");
            //или:
            //message.setText(m_appResources.getString("message.body"), "UTF-8");

//            message.setContent(m_appResources.getString("message.body"),
              message.setContent(body,
                    "text/plain;charset=\"UTF-8\"");//не захотело UTF-16
*/
        /*
         String Html = ...;
         String DefaultCharSet=
        MimeUtility.getDefaultJavaCharset();
        msg.setText(MimeUility.encodeText(Html,DefaultCharSet,"B"));
        msg.send();

        Q short for Quoted Printable
        B short for Base64
         */
/*
//            InternetAddress addrTo = new InternetAddress(m_appResources.getString("recipient.address"));
            InternetAddress addrTo = new InternetAddress(email);
            //для русского языка в имени получателя (аналогично в имени отправителя):
            try {
                addrTo.setPersonal("заец зайцев", "KOI8-R");
            } catch (java.io.UnsupportedEncodingException uee) {
                System.out.println(uee.getMessage());
            }
            message.addRecipient(Message.RecipientType.TO,
                    addrTo //Например:"=?koi8-r?Q?=E0=D2=C7=C9=CE_=E1=2E=EB=2E?=" - эти закорючки означают "Юргин А.К."
            );
            // Проверка задания натурального заголовка (работает, проще всего увидеть,задав mailSession.setDebug(true)):
            message.setHeader("X-Mailer", "org.sukhoi.mail"); //произвольное указание почтовой программы

            message.setSentDate(new Date());
            message.setHeader("Content-Transfer-Encoding", "base64"); //должно быть после setText()

            transport.connect();
            //нужен поток, т.к. возможна долгая операция.
            //сравнить с send();
            //разрешить частичную посылку, если адресов много,чтоб при одном неправильном адресе
            //не прекращало работу.

            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));

            //для отладки - список заголовков:
            Enumeration msgHeaders = message.getAllHeaders();
            System.out.println("HEADERS");
            while (msgHeaders.hasMoreElements()) {
                Header header = (Header) msgHeaders.nextElement();
                System.out.println(header.getName() + "=" + header.getValue());
            }

            transport.close();
        }
*/
        /**
         * Пытается соединиться с почтовым ящиком и получить список писем.
         */
/*
        public void fetchMessages(Session session, String host, String username, String password) {
            try {
                Store store = session.getStore("pop3");//"imap" не заработало(connection refuzed) - добавить проверку, что есть
                store.connect(host, username, password);

                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);

                Message message[] = folder.getMessages();
                for (int i=0; i < message.length; i++) {
                    System.out.println(i + ": " + message[i].getFrom()[0]//Дефект - getFrom... м.б. null
                            + "\t" + message[i].getSubject());
                }

                folder.close(false);
                store.close();
            } catch (NoSuchProviderException nspe) {
                System.out.println(nspe.getMessage());
            } catch (MessagingException me) {
                System.out.println(me.getMessage());
            }
        }
*/
        /**
         * Загрузить строковые ресурсы.
         * @param resourceName имя файла свойств (без хвоста вида "..._ru_RU.properties").
         */
/*
        private void loadResources(String resourceName) {
            m_appResources = PropertyResourceBundle.getBundle(resourceName);//TODO:использовать только для многоязычных сообщений
            //для отладки

            Enumeration keys = m_appResources.getKeys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                System.out.println(key + "=" + m_appResources.getString(key));
            }
        }
*/

}
