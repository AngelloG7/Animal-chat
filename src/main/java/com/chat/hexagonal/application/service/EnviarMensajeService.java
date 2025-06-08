// src/main/java/com/chat/hexagonal/application/service/EnviarMensajeService.java
package com.chat.hexagonal.application.service;

import com.chat.hexagonal.application.port.in.EnviarMensajeUseCase;
import com.chat.hexagonal.application.port.out.MensajeRepositoryPort;
import com.chat.hexagonal.application.port.out.UsuarioRepositoryPort;
import com.chat.hexagonal.domain.model.Mensaje;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnviarMensajeService implements EnviarMensajeUseCase {

    private final MensajeRepositoryPort mensajeRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort; // Necesario para obtener datos del remitente

    @Override
    public Mensaje enviarMensajeTexto(String contenido, String remitenteId) {
        Usuario remitente = usuarioRepositoryPort.findById(remitenteId)
                .orElseThrow(() -> new IllegalArgumentException("Remitente no encontrado."));

        Mensaje mensaje = new Mensaje(contenido, remitente.getId(), remitente.getNombreAnimal(), remitente.getColor());
        return mensajeRepositoryPort.save(mensaje);
    }

    @Override
    public Mensaje enviarMensajeImagen(String imagenUrl, String remitenteId) {
        // En una implementación real, aquí se añadirían validaciones de URL/formato de imagen.
        // Por ahora, asumimos que imagenUrl es válido.
        if (imagenUrl == null || imagenUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen no puede estar vacía.");
        }

        Usuario remitente = usuarioRepositoryPort.findById(remitenteId)
                .orElseThrow(() -> new IllegalArgumentException("Remitente no encontrado."));

        Mensaje mensaje = new Mensaje(imagenUrl, remitente.getId(), remitente.getNombreAnimal(), remitente.getColor(), "IMAGEN");
        return mensajeRepositoryPort.save(mensaje);
    }
}
