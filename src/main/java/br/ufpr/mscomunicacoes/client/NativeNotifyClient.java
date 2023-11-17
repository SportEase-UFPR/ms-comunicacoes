package br.ufpr.mscomunicacoes.client;


import br.ufpr.mscomunicacoes.model.dto.notificacao.NativeNotifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class NativeNotifyClient {

    @Value("${url.native_notify}")
    private String urlNativeNotify;

    private final RestTemplate restTemplate;

    public NativeNotifyClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public void fazerRequisicaoNotifyPush(NativeNotifyRequest request) {
        var headers = gerarCabecalho();
        CompletableFuture.runAsync(() -> {
            try {
                restTemplate.postForEntity(urlNativeNotify, new HttpEntity<>(request, headers), Void.class);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }


}
