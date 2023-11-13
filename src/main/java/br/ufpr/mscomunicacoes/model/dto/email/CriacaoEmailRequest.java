package br.ufpr.mscomunicacoes.model.dto.email;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriacaoEmailRequest {
    @NotBlank(message = "O email é obrigatório")
    private String email;
    @NotBlank(message = "O assunto é obrigatório")
    private String assunto;
    @NotBlank(message = "A mensagem é obrigatória")
    private String mensagem;
}
