package br.ufpr.mscomunicacoes.service;


import br.ufpr.mscomunicacoes.exceptions.EmailException;
import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Void enviarEmail(CriacaoEmailRequest request) {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(request.getEmail());
            helper.setSubject(request.getAssunto());
            helper.setText(request.getEmail(), true);
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
        mailSender.send(mensagem);
        return null;
    }
}
