package br.ufpr.mscomunicacoes.security;

import br.ufpr.mscomunicacoes.exceptions.TokenInvalidoException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
public class TokenService {


    @Value("${api.security.token.apigateway.secret}")
    private String apiGatewaySecret;

    @Value("${api.security.token.mscomunicacoes.secret}")
    private String msComunicacoesSecret;

    @Value("${api.security.token.mslocacoes.secret}")
    private String msLocacoesSecret;


    @Value("${api.gateway.issuer}")
    private String apiGatewayIssuer;

    @Value("${api.mscomunicacoes.issuer}")
    private String msComunicacoesIssuer;


    @Value("${api.mslocacoes.issuer}")
    private String msLocacoesIssuer;



    public void validarTokenApiGateway(String tokenJWT) {
        var tokenFormatado = removerPrefixoToken(tokenJWT);
        try {
            var algoritmo = Algorithm.HMAC256(apiGatewaySecret);
            JWT.require(algoritmo)
                    .withIssuer(apiGatewayIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    public void validarTokenApiMsLocacoes(String tokenApi) {
        var tokenFormatado = removerPrefixoToken(tokenApi);
        try {
            var algoritmo = Algorithm.HMAC256(msLocacoesSecret);
            JWT.require(algoritmo)
                    .withIssuer(msLocacoesIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    public void validarTokenApi(String tokenApi) {
        var tokenFormatado = removerPrefixoToken(tokenApi);

        for (int i = 0; i < issuers.size(); i++) {
            try {
                var algoritmo = Algorithm.HMAC256(secrets.get(i));
                JWT.require(algoritmo)
                        .withIssuer(issuers.get(i))
                        .build()
                        .verify(tokenFormatado);

                // Se a verificação for bem-sucedida, retornar ou registrar o sucesso, dependendo do caso.
                return;
            } catch (JWTVerificationException ignored) {
                // Ignorar exceções e tentar com a próxima combinação de issuer e secret.
            }
        }

        // Se nenhum issuer e secret combinado for bem-sucedido, lançar uma exceção.
        log.error("Token JWT inválido ou expirado");
        throw new TokenInvalidoException("Token JWT inválido ou expirado");
    }


    public String gerarTokenMsComunicacoes() {
        var algoritmo = Algorithm.HMAC256(msComunicacoesSecret);
        return JWT.create()
                .withIssuer(msComunicacoesIssuer)
                .withSubject(msComunicacoesIssuer)
                .withExpiresAt(dataExpiracao(20)) //data da expiração
                .sign(algoritmo); //assinatura
    }

    public String removerPrefixoToken(String token) {
        return token.replace("Bearer ", "");
    }

    //recupera um issuer do token
    public String getIssuer(String tokenJWT, String issuer) {
        return JWT.decode(tokenJWT).getClaim(issuer).asString();
    }

    //recupera o subject do token
    public String getSubject(String tokenJWT) {
        return JWT.decode(tokenJWT).getSubject();
    }

    private Instant dataExpiracao(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }


}
