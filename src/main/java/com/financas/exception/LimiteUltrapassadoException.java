package com.financas.exception;

public class LimiteUltrapassadoException extends RuntimeException {
    public LimiteUltrapassadoException(String message) {
        super(message);
    }
}
