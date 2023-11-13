package br.ufpr.mscomunicacoes.controller;


import br.ufpr.mscomunicacoes.client.MsCadastrosClient;
import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;

import br.ufpr.mscomunicacoes.model.dto.email.EnviarEmailRequest;
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
    private TokenService tokenService;
    private EmailService emailService;
    private MsCadastrosClient msCadastrosClient;

    @PostMapping
    public ResponseEntity<Void> enviarEmailViaApiGateway(@RequestBody @Valid CriacaoEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmail(request));
    }

    @PostMapping("/via-ms")
    public ResponseEntity<Void> enviarEmailViaMs(@RequestBody @Valid CriacaoEmailRequest request,
                                                 @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmail(request));
    }

    @PostMapping
    public ResponseEntity<Void> enviarEmailClientes(@RequestBody @Valid EnviarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.enviarEmailClientes(request));
    }
    @PostMapping("/todos")
    public ResponseEntity<Void> enviarEmailTodosClientes(@RequestBody @Valid EnviarEmailTodosRequest request) {
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
