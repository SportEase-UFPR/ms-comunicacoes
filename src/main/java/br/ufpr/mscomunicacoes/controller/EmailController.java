package br.ufpr.mscomunicacoes.controller;


import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;

import br.ufpr.mscomunicacoes.security.TokenService;
import br.ufpr.mscomunicacoes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/email")
public class EmailController {
    private TokenService tokenService;
    private EmailService emailService;

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


}
