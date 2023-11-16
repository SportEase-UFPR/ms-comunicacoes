package br.ufpr.mscomunicacoes.controller;


import br.ufpr.mscomunicacoes.client.MsCadastrosClient;
import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;

import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailRequest;
import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailResponse;
import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailTodosRequest;
import br.ufpr.mscomunicacoes.security.TokenService;
import br.ufpr.mscomunicacoes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/email")
public class EmailController {
    private final TokenService tokenService;
    private final EmailService emailService;
    private final MsCadastrosClient msCadastrosClient;

    public EmailController(TokenService tokenService, EmailService emailService, MsCadastrosClient msCadastrosClient) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.msCadastrosClient = msCadastrosClient;
    }

    @PostMapping("via-api-gateway")
    public ResponseEntity<EnviarEmailResponse> enviarEmailViaApiGateway(@RequestBody @Valid CriacaoEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmail(request));
    }

    @PostMapping("/via-ms")
    public ResponseEntity<EnviarEmailResponse> enviarEmailViaMs(@RequestBody @Valid CriacaoEmailRequest request,
                                                 @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmail(request));
    }

    @PostMapping
    public ResponseEntity<EnviarEmailResponse> enviarEmailClientes(@RequestBody @Valid EnviarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmailClientes(request));
    }
    @PostMapping("/todos")
    public ResponseEntity<EnviarEmailResponse> enviarEmailTodosClientes(@RequestBody @Valid EnviarEmailTodosRequest request) {
        var listaBuscarEmailsClientes = msCadastrosClient.buscarEmailsClientes();
        var listaEmailsClientes = new ArrayList<String>();

        listaBuscarEmailsClientes.forEach(cliente -> listaEmailsClientes.add(cliente.getEmailCliente()));

        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmailClientes(EnviarEmailRequest.builder()
                        .assunto(request.getAssunto())
                        .corpo(request.getCorpo())
                        .listaEmails(listaEmailsClientes)
                .build()));
    }


}
