package br.ufpr.mscomunicacoes.client;


import br.ufpr.mscomunicacoes.model.dto.notificacao.NativeNotifyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
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
        restTemplate.exchange(urlNativeNotify, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class);
    }


}
