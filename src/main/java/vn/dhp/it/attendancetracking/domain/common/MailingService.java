/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring4.SpringTemplateEngine;
import vn.dhp.it.attendancetracking.domain.user.User;
import vn.dhp.it.attendancetracking.domain.util.EmailTemplateWrapper;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Future;

@Service
@Slf4j
public class MailingService {
    private static final String NAME_OF_USER = "username";
    private static final String PASSWORD = "password";
    private static final String HOST_DOMAIN = "baseUrl";
    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;
    private MessageSource messageSource;

    @Value("${app.mail-sender}")
    private String sender;


    @Value("${app.host-domain}")
    private String hostDomain;

    @Autowired
    public MailingService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine,
                          MessageSource messageSource) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }

    @Async
    public Future<Boolean> sendEmailUsingTemplateAsync(EmailTemplateWrapper emailTemplate) {
        String recipient = emailTemplate.getReceiver();

        emailTemplate
            .setMessageSource(messageSource)
            .setTemplateEngine(templateEngine).make();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        boolean result;
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "utf-8");
            message.setFrom(sender);
            message.setTo(recipient);
            message.setSubject(emailTemplate.getSubject());
            message.setText(emailTemplate.getHtmlContent(), true);
            javaMailSender.send(mimeMessage);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return new AsyncResult<Boolean>(result);
    }

    @Async
    public Future<Boolean> sendActivationEmailAsync(User user, String password) {
        try {
            EmailTemplateWrapper emailTemplate = new EmailTemplateWrapper();

            emailTemplate.setHtmlTemplate("mailing/activation")
                .setSubject("Attendance Tracking Account")
                .setVariable(NAME_OF_USER, user.getUsername())
                .setVariable(HOST_DOMAIN, hostDomain)
                .setVariable(PASSWORD, password)
                .setReceiver(user.getEmail());

            sendEmailUsingTemplateAsync(emailTemplate);


        } catch (Exception e) {
            return new AsyncResult<Boolean>(false);
        }
        return new AsyncResult<Boolean>(true);
    }

}
