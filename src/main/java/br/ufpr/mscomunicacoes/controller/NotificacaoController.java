package br.ufpr.mscomunicacoes.controller;

import br.ufpr.mscomunicacoes.model.dto.notificacao.CriacaoNotificacaoRequest;
import br.ufpr.mscomunicacoes.model.dto.notificacao.NotificacaoResponse;
import br.ufpr.mscomunicacoes.security.TokenService;
import br.ufpr.mscomunicacoes.service.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;
    private final TokenService tokenService;

    public NotificacaoController(NotificacaoService notificacaoService, TokenService tokenService) {
        this.notificacaoService = notificacaoService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<NotificacaoResponse> criarNotificacao(@RequestBody @Valid CriacaoNotificacaoRequest request,
                                                                @RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacaoService.criarNotificacao(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoResponse>> buscarNotificacoesCliente(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacaoService.buscarNotificacoesCliente(token));
    }

    @PutMapping("/marcar-como-lida")
    public ResponseEntity<Void> marcarNotificacoesComoLida(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(notificacaoService.marcarNotificacoesComoLida(token));

    }
}
