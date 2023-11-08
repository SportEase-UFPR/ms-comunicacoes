package br.ufpr.mscomunicacoes.model.dto.notificacao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriacaoNotificacaoRequest {
    @NotBlank(message = "O idCliente é obrigatório. Use idCliente=-1 para enviar notificação a todos os clientes")
    private Long idCliente;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "O conteúdo é obrigatório")
    private String conteudo;

}
