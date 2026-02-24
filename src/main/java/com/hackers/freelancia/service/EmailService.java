package com.hackers.freelancia.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * Envoie un email d'activation de compte à l'utilisateur.
     *
     * @param to    l'adresse email du destinataire
     * @param token le token d'activation
     * @throws MessagingException en cas d'erreur lors de l'envoi de l'email
     */
    public void sendActivationEmail(String to, String token) throws MessagingException {

        Context context = new Context();
        context.setVariable("activationLink",
                "http://192.168.1.86:33726/api/v1/auth/activate?token=" + token);

        String html = templateEngine.process("activation-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Activation de votre compte Freelancia");
        helper.setText(html, true);

        mailSender.send(message);
    }

    /**
     * Envoie un email de réinitialisation de mot de passe à l'utilisateur.
     *
     * @param to    l'adresse email du destinataire
     * @param token le token de réinitialisation de mot de passe
     * @throws MessagingException en cas d'erreur lors de l'envoi de l'email
     */
    public void sendPasswordResetEmail(String to, String token) throws MessagingException {

        Context context = new Context();
        context.setVariable("resetLink",
                "http://192.168.1.86:33726/api/v1/auth/reset-password?token=" + token);

        String html = templateEngine.process("reset-password-email", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Réinitialisation de votre mot de passe");
        helper.setText(html, true);

        mailSender.send(message);
    }

}
