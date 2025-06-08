// src/main/java/com/chat/hexagonal/domain/exception/NombreAnimalDuplicadoException.java
package com.chat.hexagonal.domain.exception;

public class NombreAnimalDuplicadoException extends RuntimeException {

    public NombreAnimalDuplicadoException(String message) {
        super(message);
    }

    public NombreAnimalDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
