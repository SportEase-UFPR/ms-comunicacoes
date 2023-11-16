package br.ufpr.mscomunicacoes.model.dto.email;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnviarEmailResponse {
    private String mensagem;
}
