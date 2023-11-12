package br.ufpr.mscomunicacoes.controller;


import br.ufpr.mscomunicacoes.model.dto.email.CriacaoEmailRequest;

import br.ufpr.mscomunicacoes.security.TokenService;
import br.ufpr.mscomunicacoes.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/email")
public class EmailController {
    private TokenService tokenService;
    private EmailService emailService;
    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody @Valid CriacaoEmailRequest request,
                                                                @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenApi(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.enviarEmail(request));
    }

}
