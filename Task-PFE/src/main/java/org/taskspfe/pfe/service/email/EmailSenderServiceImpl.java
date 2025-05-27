package org.taskspfe.pfe.service.email;

import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl  implements  EmailSenderService{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailSenderServiceImpl.class);
    private final JavaMailSender javaMailSender;

    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendEmail(final String toEmail , final String subject, String body)
    {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("rahma@pfe.com");
            javaMailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String emailTemplateContact(String subject, String description) {
        return "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px;\">\n" +
                "    <div style=\"background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 100%; max-width: 600px; margin: 0 auto;\">\n" +
                "        <h2 style=\"margin-bottom: 20px; text-align: center;\">Reclamation</h2>\n" +
                "        <div style=\"margin-bottom: 15px;\">\n" +
                "            <h3 style=\"margin-bottom: 5px;\">Subject</h3>\n" +
                "            <p style=\"padding: 10px; border: 1px solid #ccc; border-radius: 4px; background-color: #f9f9f9;\">"+subject+".</p>\n" +
                "        </div>\n" +
                "        <div style=\"margin-bottom: 15px;\">\n" +
                "            <h3 style=\"margin-bottom: 5px;\">Description</h3>\n" +
                "            <p style=\"padding: 10px; border: 1px solid #ccc; border-radius: 4px; background-color: #f9f9f9;\">"+description + ".</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>";
    }


}
