// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/MensajeMongoAdapter.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence;

import com.chat.hexagonal.application.port.out.MensajeRepositoryPort;
import com.chat.hexagonal.domain.model.Mensaje;
import com.chat.hexagonal.domain.model.Reaccion;
import com.chat.hexagonal.infrastructure.adapter.out.persistence.data.MensajeDocument;
import com.chat.hexagonal.infrastructure.adapter.out.persistence.data.ReaccionDocument;
import com.chat.hexagonal.infrastructure.adapter.out.persistence.springdata.MensajeMongoSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MensajeMongoAdapter implements MensajeRepositoryPort {

    private final MensajeMongoSpringRepository mensajeMongoSpringRepository;

    @Override
    public Mensaje save(Mensaje mensaje) {
        MensajeDocument mensajeDocument = toMensajeDocument(mensaje);
        MensajeDocument savedDocument = mensajeMongoSpringRepository.save(mensajeDocument);
        return toMensaje(savedDocument);
    }

    @Override
    public List<Mensaje> findRecentMessages(LocalDateTime sinceFecha) {
        List<MensajeDocument> documents = mensajeMongoSpringRepository.findByFechaEnvioAfterOrderByFechaEnvioDesc(sinceFecha);
        return documents.stream()
                .map(this::toMensaje)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensaje> searchMessages(String keyword, String userId) {
        List<MensajeDocument> documents;
        String regexKeyword = ".*" + keyword + ".*"; // Preparar la palabra clave para búsqueda regex

        if (userId != null && !userId.isEmpty()) {
            documents = mensajeMongoSpringRepository.findByContenidoRegexOrNombreRemitenteRegexAndIdRemitente(regexKeyword, userId);
        } else {
            documents = mensajeMongoSpringRepository.findByContenidoRegexOrNombreRemitenteRegex(regexKeyword);
        }
        return documents.stream()
                .map(this::toMensaje)
                .collect(Collectors.toList());
    }


    // El método deleteExpiredMessages no necesita una implementación directa aquí si usamos TTL Index.
    // MongoDB se encarga de esto automáticamente. Si necesitáramos una eliminación manual,
    // podríamos añadirla aquí e invocarla, por ejemplo, con un @Scheduled task en otro lado.
    @Override
    public void deleteExpiredMessages(LocalDateTime expirationDate) {
        // Con @Indexed(expireAfterSeconds = 0) en fechaCaducidad, MongoDB maneja la eliminación automáticamente.
        // No necesitamos una implementación explícita aquí a menos que queramos una lógica de eliminación adicional.
        // Podríamos, por ejemplo, borrar mensajes que no tienen fechaCaducidad o que tienen valores inconsistentes.
    }


    // --- Métodos de Conversión (Mappers) ---

    private MensajeDocument toMensajeDocument(Mensaje mensaje) {
        return MensajeDocument.builder()
                .id(mensaje.getId())
                .contenido(mensaje.getContenido())
                .tipo(mensaje.getTipo())
                .idRemitente(mensaje.getIdRemitente())
                .nombreRemitente(mensaje.getNombreRemitente())
                .colorRemitente(mensaje.getColorRemitente())
                .fechaEnvio(mensaje.getFechaEnvio())
                .fechaCaducidad(mensaje.getFechaCaducidad())
                .reacciones( Optional.ofNullable(mensaje.getReacciones())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(this::toReaccionDocument)
                                .collect(Collectors.toList()))
                .build();
    }

    private Mensaje toMensaje(MensajeDocument document) {
        return Mensaje.builder()
                .id(document.getId())
                .contenido(document.getContenido())
                .tipo(document.getTipo())
                .idRemitente(document.getIdRemitente())
                .nombreRemitente(document.getNombreRemitente())
                .colorRemitente(document.getColorRemitente())
                .fechaEnvio(document.getFechaEnvio())
                .fechaCaducidad(document.getFechaCaducidad())
                .reacciones( Optional.ofNullable(document.getReacciones())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(this::toReaccion)
                                .collect(Collectors.toList()))
                .build();
    }

    // Métodos de conversión para Reaccion
    private ReaccionDocument toReaccionDocument(Reaccion reaccion) {
        return ReaccionDocument.builder()
                .userId(reaccion.getUserId())
                .emoji(reaccion.getEmoji())
                .build();
    }

    private Reaccion toReaccion(ReaccionDocument document) {
        return Reaccion.builder()
                .userId(document.getUserId())
                .emoji(document.getEmoji())
                .build();
    }
}
