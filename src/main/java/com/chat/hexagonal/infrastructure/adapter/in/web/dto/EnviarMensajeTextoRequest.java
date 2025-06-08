// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/EnviarMensajeTextoRequest.java
package com.chat.hexagonal.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnviarMensajeTextoRequest {
    private String contenido;
    private String idRemitente;
}
