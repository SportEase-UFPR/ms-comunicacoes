package br.ufpr.mscomunicacoes.exceptions;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String msg) {
        super(msg);
    }
}
