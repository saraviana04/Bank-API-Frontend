package com.bankapi.dto;

public class UsuarioCriadoResponse {
    private String message;
    private String numeroConta;

    public UsuarioCriadoResponse() {
    }

    public UsuarioCriadoResponse(String message, String numeroConta) {
        this.message = message;
        this.numeroConta = numeroConta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
