// src/main/java/com/chat/hexagonal/application/port/in/BuscarMensajesUseCase.java
package com.chat.hexagonal.application.port.in;

import com.chat.hexagonal.domain.model.Mensaje;

import java.util.List;

public interface BuscarMensajesUseCase {

    /**
     * Realiza una búsqueda de mensajes por palabra clave y opcionalmente por ID de usuario.
     * @param keyword La palabra clave a buscar en el contenido del mensaje o nombre del remitente.
     * @param userId Opcional: El ID del usuario para filtrar los mensajes. Si es nulo, busca en todos los usuarios.
     * @return Una lista de Mensajes que coinciden con los criterios de búsqueda.
     */
    List<Mensaje> buscarMensajes(String keyword, String userId);
}
