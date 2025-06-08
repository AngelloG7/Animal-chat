// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/data/MensajeDocument.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "mensajes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDocument {

    @Id
    private String id;
    private String contenido;
    private String tipo;
    private String idRemitente;
    private String nombreRemitente;
    private String colorRemitente;
    @Indexed(expireAfterSeconds = 0) // Hace que MongoDB elimine documentos después de fechaCaducidad
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaCaducidad; // Campo usado por @Indexed(expireAfterSeconds = 0)
    private List<ReaccionDocument> reacciones; // Para futuras reacciones
}
