package dds.grupo4.tpimpacto.common;

import jakarta.mail.MessagingException;

public interface MailSender {
    void sendMail(String to, String subject, String msg) throws MessagingException;
}
