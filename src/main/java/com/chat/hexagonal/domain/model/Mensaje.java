// src/main/java/com/chat/hexagonal/domain/model/Mensaje.java
package com.chat.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List; // Para las reacciones

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mensaje {
    private String id;
    private String contenido; // El texto del mensaje o el URL/ID de la imagen
    private String tipo; // "TEXTO", "IMAGEN"
    private String idRemitente; // ID del usuario que envió el mensaje
    private String nombreRemitente; // Nombre del animal del usuario que envió el mensaje
    private String colorRemitente; // Color del usuario que envió el mensaje
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaCaducidad; // Para la eliminación automática
    private List<Reaccion> reacciones; // Lista de reacciones al mensaje (lo implementaremos después)

    // Constructor para mensajes de texto
    public Mensaje(String contenido, String idRemitente, String nombreRemitente, String colorRemitente) {
        this.contenido = contenido;
        this.tipo = "TEXTO";
        this.idRemitente = idRemitente;
        this.nombreRemitente = nombreRemitente;
        this.colorRemitente = colorRemitente;
        this.fechaEnvio = LocalDateTime.now();
        // Los mensajes caducan después de 2 semanas
        this.fechaCaducidad = this.fechaEnvio.plusWeeks(2);
    }

    // Constructor para mensajes de imagen (el contenido es la URL/ID de la imagen)
    public Mensaje(String contenido, String idRemitente, String nombreRemitente, String colorRemitente, String tipoImagen) {
        if (!tipoImagen.equals("IMAGEN")) {
            throw new IllegalArgumentException("El tipo debe ser 'IMAGEN' para este constructor.");
        }
        this.contenido = contenido; // Aquí irá el ID o URL de la imagen
        this.tipo = tipoImagen;
        this.idRemitente = idRemitente;
        this.nombreRemitente = nombreRemitente;
        this.colorRemitente = colorRemitente;
        this.fechaEnvio = LocalDateTime.now();
        this.fechaCaducidad = this.fechaEnvio.plusWeeks(2);
    }
}
