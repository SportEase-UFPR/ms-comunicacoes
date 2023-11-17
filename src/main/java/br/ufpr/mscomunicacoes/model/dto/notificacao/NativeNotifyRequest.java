package br.ufpr.mscomunicacoes.model.dto.notificacao;


import lombok.*;

@Getter
@Setter
public class NativeNotifyRequest {
    private Integer appId;
    private String appToken;
    private String subID;
    private String title;
    private String message;

    public NativeNotifyRequest(String subID, String title, String message) {
        this.appId = 14520;
        this.appToken = "vdk0Ur8ZprhkWw4MiubMKt";
        this.subID = subID;
        this.title = title;
        this.message = message;
    }


}