package br.ufpr.mscomunicacoes.service;


import br.ufpr.mscomunicacoes.exceptions.EmailException;
import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;
import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailRequest;
import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EnviarEmailResponse  enviarEmail(CriacaoEmailRequest request) {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mensagem, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setTo(request.getEmail());
            helper.setSubject(request.getAssunto());
            helper.setText(request.getMensagem(), true);
        } catch (MessagingException e) {
            throw new EmailException(e.getMessage());
        }
        mailSender.send(mensagem);
        return EnviarEmailResponse.builder()
                .mensagem("emails enviados com sucesso")
                .build();
    }

    public EnviarEmailResponse enviarEmailClientes(EnviarEmailRequest request) {
        List<String> listaEmails = request.getListaEmails();
        String assunto = request.getAssunto();
        String corpo = request.getCorpo();

        ExecutorService executorService = Executors.newFixedThreadPool(10); //número de threads

        for (String email : listaEmails) {
            executorService.submit(() ->
                enviarEmail(CriacaoEmailRequest.builder()
                        .assunto(assunto)
                        .mensagem(corpo)
                        .email(email)
                        .build()));
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {}
        return EnviarEmailResponse.builder()
                .mensagem("emails enviados com sucesso")
                .build();
    }

}
