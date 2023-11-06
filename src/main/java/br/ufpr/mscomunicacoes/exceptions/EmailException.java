package br.ufpr.mscomunicacoes.exceptions;

public class EmailException extends RuntimeException {
    public EmailException(String msg) {
        super(msg);
    }
}