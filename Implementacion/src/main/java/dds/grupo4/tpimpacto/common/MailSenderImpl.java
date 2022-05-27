package dds.grupo4.tpimpacto.common;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Date;
import java.util.Properties;

public class MailSenderImpl implements MailSender {

    @Override
    public void sendMail(String to, String subject, String msg) throws MessagingException {
        String from = "nuestromail@gmail.com";
        String host = "TODO: VerQueVaAca";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setSentDate(new Date());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
