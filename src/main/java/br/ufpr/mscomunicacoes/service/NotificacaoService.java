package br.ufpr.mscomunicacoes.service;

import br.ufpr.mscomunicacoes.client.MsCadastrosClient;
import br.ufpr.mscomunicacoes.exceptions.EntityNotFoundException;
import br.ufpr.mscomunicacoes.model.dto.notificacao.CriacaoNotificacaoRequest;
import br.ufpr.mscomunicacoes.model.dto.notificacao.NotificacaoResponse;
import br.ufpr.mscomunicacoes.model.entity.Notificacao;
import br.ufpr.mscomunicacoes.repository.NotificacaoRepository;
import br.ufpr.mscomunicacoes.security.TokenService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacaoService {
    private final NotificacaoRepository repository;
    private final MsCadastrosClient msCadastrosClient;
    private final TokenService tokenService;

    public NotificacaoService(NotificacaoRepository repository, MsCadastrosClient msCadastrosClient, TokenService tokenService) {
        this.repository = repository;
        this.msCadastrosClient = msCadastrosClient;
        this.tokenService = tokenService;
    }

    public NotificacaoResponse criarNotificacao(CriacaoNotificacaoRequest request) {
        if(request.getIdCliente() == -1) {
            return criarNotificacaoParaTodos(request);
        } else {
            return criarNotificacaoIndividual(request);
        }
    }

    private NotificacaoResponse criarNotificacaoParaTodos(CriacaoNotificacaoRequest request) {
        List<Long> listaIdsClientes =  msCadastrosClient.buscarIdsClientes();

        listaIdsClientes.forEach(id -> {
            request.setIdCliente(id);
            criarNotificacaoIndividual(request);
        });

        return NotificacaoResponse.builder()
                .id(-1L)
                .idCliente(-1L)
                .titulo(request.getTitulo())
                .conteudo(request.getConteudo())
                .lida(false)
                .build();
    }

    private NotificacaoResponse criarNotificacaoIndividual(CriacaoNotificacaoRequest request) {
        var novaNotificacao = new Notificacao(request);
        repository.save(novaNotificacao);
        return new NotificacaoResponse(novaNotificacao);
    }

    public List<NotificacaoResponse> buscarNotificacoesCliente(String token) {
        var idCliente = Long.parseLong(tokenService.getIssuer(token, "idPessoa"));

        var listaNotificacoes = repository.findByIdCliente(idCliente);
        listaNotificacoes.addAll(repository.findByIdCliente(-1L));
        if(listaNotificacoes.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma notificação encontrada");
        }

        var response = new ArrayList<NotificacaoResponse>();
        listaNotificacoes.forEach(notificacao -> response.add(new NotificacaoResponse(notificacao)));
        return response;
    }

    public Void marcarNotificacoesComoLida(String token) {
        var idCliente = Long.parseLong(tokenService.getIssuer(token, "idPessoa"));

        repository.marcarNotificacoesComoLida(idCliente);
        return null;
    }
}
