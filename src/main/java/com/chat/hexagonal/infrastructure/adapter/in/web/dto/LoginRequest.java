// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/LoginRequest.java
package com.chat.hexagonal.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String nombreAnimal;
    private String clave;
}
