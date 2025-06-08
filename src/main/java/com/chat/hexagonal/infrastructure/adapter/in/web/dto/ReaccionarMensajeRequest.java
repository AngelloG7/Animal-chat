// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/ReaccionarMensajeRequest.java
package com.chat.hexagonal.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaccionarMensajeRequest {
    private String userId;
    private String emoji;
}
