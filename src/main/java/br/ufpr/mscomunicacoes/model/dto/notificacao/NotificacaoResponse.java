package br.ufpr.mscomunicacoes.model.dto.notificacao;

import br.ufpr.mscomunicacoes.model.entity.Notificacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NotificacaoResponse {
    private Long id;

    private Long idCliente;

    private String titulo;

    private String conteudo;

    private Boolean lida;

    private LocalDateTime dataHora;

    public NotificacaoResponse(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.idCliente = notificacao.getIdCliente();
        this.titulo = notificacao.getTitulo();
        this.conteudo = notificacao.getConteudo();
        this.lida = notificacao.getLida();
        this.dataHora = notificacao.getDataHora();
    }
}
