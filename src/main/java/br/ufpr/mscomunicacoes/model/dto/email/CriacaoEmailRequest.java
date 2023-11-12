package br.ufpr.mscomunicacoes.model.dto.email;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriacaoEmailRequest {
    @NotBlank(message = "O email é obrigatório")
    private String email;
    @NotBlank(message = "O assunto é obrigatório")
    private String assunto;
    @NotBlank(message = "A mensagem é obrigatória")
    private String mensagem;
}
