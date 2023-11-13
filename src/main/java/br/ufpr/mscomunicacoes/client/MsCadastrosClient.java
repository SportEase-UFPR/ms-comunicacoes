package br.ufpr.mscomunicacoes.client;


import br.ufpr.mscomunicacoes.model.dto.cliente.BuscarEmailsClientesResponse;
import br.ufpr.mscomunicacoes.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MsCadastrosClient {

    @Value("${url.ms.cadastros.clientes}")
    private String urlMsCadastros;

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public MsCadastrosClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("AuthorizationApi", tokenService.gerarTokenMs());
        return headers;
    }


    public List<Long> buscarIdsClientes() {
        String url = urlMsCadastros + "/clientes/buscar-ids";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Long>>() {}).getBody();
    }

    public List<BuscarEmailsClientesResponse> buscarEmailsClientes() {
        String url = urlMsCadastros + "/clientes/buscar-emails-clientes/via-ms";
        HttpHeaders headers = gerarCabecalho();
        var response =  restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

        var listaBuscarEmailsClientes = new ArrayList<BuscarEmailsClientesResponse>();

        assert response != null;
        response.forEach(obj -> listaBuscarEmailsClientes.add(new BuscarEmailsClientesResponse(obj)));

        return listaBuscarEmailsClientes;
    }
}
