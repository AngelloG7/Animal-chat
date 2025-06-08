// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/data/ReaccionDocument.java
// (Crear esta clase para evitar errores de compilación con MensajeDocument)
package com.chat.hexagonal.infrastructure.adapter.out.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// No es un @Document principal, es un objeto incrustado o sub-documento
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReaccionDocument {
    private String userId;
    private String emoji;
}
