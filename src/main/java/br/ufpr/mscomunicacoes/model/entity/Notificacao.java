package br.ufpr.mscomunicacoes.model.entity;

import br.ufpr.mscomunicacoes.model.dto.notificacao.CriacaoNotificacaoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_notificacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idCliente;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private Boolean lida;

    public Notificacao(CriacaoNotificacaoRequest request) {
        this.idCliente = request.getIdCliente();
        this.titulo = request.getTitulo();
        this.conteudo = request.getConteudo();
        this.lida = false;
    }
}
