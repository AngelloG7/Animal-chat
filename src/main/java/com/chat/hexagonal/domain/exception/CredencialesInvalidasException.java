// src/main/java/com/chat/hexagonal/domain/exception/CredencialesInvalidasException.java
package com.chat.hexagonal.domain.exception;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException(String message) {
        super(message);
    }

    public CredencialesInvalidasException(String message, Throwable cause) {
        super(message, cause);
    }
}
