// src/main/java/com/chat/hexagonal/application/service/GestionarReaccionService.java
package com.chat.hexagonal.application.service;

import com.chat.hexagonal.application.port.in.GestionarReaccionUseCase;
import com.chat.hexagonal.application.port.out.MensajeRepositoryPort;
import com.chat.hexagonal.application.port.out.UsuarioRepositoryPort;
import com.chat.hexagonal.domain.model.Mensaje;
import com.chat.hexagonal.domain.model.Reaccion;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar para transacciones

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionarReaccionService implements GestionarReaccionUseCase {

    private final MensajeRepositoryPort mensajeRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort; // Para validar que el usuario existe

    @Override
    @Transactional // Asegura que la operación sea atómica, importante para actualizaciones
    public Mensaje toggleReaccion(String mensajeId, String userId, String emoji) {
        // 1. Validar que el usuario existe
        Usuario usuario = usuarioRepositoryPort.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        // 2. Obtener el mensaje al que se quiere reaccionar
        Mensaje mensaje = mensajeRepositoryPort.findById(mensajeId)
                .orElseThrow(() -> new IllegalArgumentException("Mensaje no encontrado con ID: " + mensajeId));

        // 3. Gestionar la reacción (agregar o remover)
        List<Reaccion> reaccionesActuales = new ArrayList<>(mensaje.getReacciones() != null ? mensaje.getReacciones() : new ArrayList<>());
        boolean reaccionEncontrada = false;

        // Iterar de forma segura para remover elementos
        Iterator<Reaccion> iterator = reaccionesActuales.iterator();
        while (iterator.hasNext()) {
            Reaccion r = iterator.next();
            // Si el mismo usuario ya reaccionó con el mismo emoji, la removemos (toggle)
            if (r.getUserId().equals(userId) && r.getEmoji().equals(emoji)) {
                iterator.remove();
                reaccionEncontrada = true;
                break; // Ya la encontramos y removemos, salimos
            }
        }

        // Si la reacción no fue encontrada y removida, entonces la agregamos
        if (!reaccionEncontrada) {
            reaccionesActuales.add(new Reaccion(userId, emoji));
        }

        // 4. Actualizar la lista de reacciones en el mensaje
        mensaje.setReacciones(reaccionesActuales);

        // 5. Guardar el mensaje actualizado
        return mensajeRepositoryPort.save(mensaje);
    }
}
