// src/main/java/com/chat/hexagonal/application/service/BuscarMensajesService.java
package com.chat.hexagonal.application.service;

import com.chat.hexagonal.application.port.in.BuscarMensajesUseCase;
import com.chat.hexagonal.application.port.out.MensajeRepositoryPort;
import com.chat.hexagonal.domain.model.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marca esta clase como un servicio de Spring
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección de dependencias
public class BuscarMensajesService implements BuscarMensajesUseCase {

    private final MensajeRepositoryPort mensajeRepositoryPort;

    @Override
    public List<Mensaje> buscarMensajes(String keyword, String userId) {
        // La lógica de búsqueda real (manejo de regex, filtrado) ya está en el adaptador de persistencia.
        // Este servicio simplemente delega la llamada.
        return mensajeRepositoryPort.searchMessages(keyword, userId);
    }
}
