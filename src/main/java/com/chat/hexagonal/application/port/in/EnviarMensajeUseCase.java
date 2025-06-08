// src/main/java/com/chat/hexagonal/application/port/in/EnviarMensajeUseCase.java
package com.chat.hexagonal.application.port.in;

import com.chat.hexagonal.domain.model.Mensaje;
import com.chat.hexagonal.domain.model.Usuario; // Necesario para obtener datos del remitente

public interface EnviarMensajeUseCase {

    /**
     * Envía un mensaje de texto.
     * @param contenido El texto del mensaje.
     * @param remitenteId El ID del usuario que envía el mensaje.
     * @return El Mensaje enviado.
     */
    Mensaje enviarMensajeTexto(String contenido, String remitenteId);

    /**
     * Envía un mensaje de imagen.
     * @param imagenUrl La URL o ID de la imagen (podría ser Base64 temporalmente).
     * @param remitenteId El ID del usuario que envía el mensaje.
     * @return El Mensaje enviado.
     * @throws IllegalArgumentException si el formato de la imagen no es válido.
     */
    Mensaje enviarMensajeImagen(String imagenUrl, String remitenteId);
}
