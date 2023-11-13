package br.ufpr.mscomunicacoes.model.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BuscarEmailsClientesResponse {
    private Long idCliente;
    private String nomeCliente;
    private String emailCliente;

    public BuscarEmailsClientesResponse(Object obj) {
        if (obj instanceof LinkedHashMap<?, ?> hm) {
            this.idCliente = Long.valueOf((Integer) hm.get("idCliente"));
            this.nomeCliente = (String) hm.get("nomeCliente");
            this.emailCliente = (String) hm.get("emailCliente");
        }
    }
}
