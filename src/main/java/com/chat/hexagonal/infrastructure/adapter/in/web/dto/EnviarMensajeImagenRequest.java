// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/EnviarMensajeImagenRequest.java
package com.chat.hexagonal.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnviarMensajeImagenRequest {
    private String imagenUrl;
    private String idRemitente;
}
