// src/main/java/com/chat/hexagonal/application/port/out/MensajeRepositoryPort.java
package com.chat.hexagonal.application.port.out;

import com.chat.hexagonal.domain.model.Mensaje;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MensajeRepositoryPort {

    /**
     * Guarda un nuevo mensaje en la base de datos.
     * @param mensaje El objeto Mensaje a guardar.
     * @return El Mensaje guardado, que podría incluir un ID generado.
     */
    Mensaje save(Mensaje mensaje);

    /**
     * Busca los últimos mensajes enviados en el chat grupal.
     * @param sinceFecha Los mensajes deben ser posteriores a esta fecha.
     * @return Una lista de mensajes, ordenados por fecha de envío.
     */
    List<Mensaje> findRecentMessages(LocalDateTime sinceFecha);

    /**
     * Busca mensajes por palabra clave en el contenido o por nombre de remitente.
     * @param keyword Palabra clave a buscar.
     * @param userId Opcional: ID del usuario remitente.
     * @return Lista de mensajes que coinciden con la búsqueda.
     */
    List<Mensaje> searchMessages(String keyword, String userId);

    /**
     * Elimina mensajes que hayan caducado antes de una fecha dada.
     * @param expirationDate Fecha límite de caducidad.
     */
    Optional<Mensaje> findById(String id); // <-- Nuevo metodo implementado
    
    void deleteExpiredMessages(LocalDateTime expirationDate);
}
