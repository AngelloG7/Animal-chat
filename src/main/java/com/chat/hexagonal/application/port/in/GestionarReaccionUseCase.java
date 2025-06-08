// src/main/java/com/chat/hexagonal/application/port/in/GestionarReaccionUseCase.java
package com.chat.hexagonal.application.port.in;

import com.chat.hexagonal.domain.model.Mensaje;

public interface GestionarReaccionUseCase {

    /**
     * Agrega o remueve una reacción de un mensaje.
     * Si el usuario ya reaccionó con el mismo emoji, se remueve.
     * Si no, se agrega.
     * Si el usuario ya reaccionó con otro emoji, no hace nada (o lo reemplaza, según la lógica deseada).
     *
     * @param mensajeId El ID del mensaje al que se va a reaccionar.
     * @param userId El ID del usuario que reacciona.
     * @param emoji El emoji de la reacción (ej. "👍", "❤️").
     * @return El Mensaje actualizado con la reacción.
     * @throws IllegalArgumentException si el mensaje o el usuario no existen.
     */
    Mensaje toggleReaccion(String mensajeId, String userId, String emoji);
}
