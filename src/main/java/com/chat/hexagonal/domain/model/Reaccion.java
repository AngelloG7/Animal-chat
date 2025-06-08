// src/main/java/com/chat/hexagonal/domain/model/Reaccion.java
package com.chat.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaccion {
    private String userId;
    private String emoji; // Por ejemplo, "👍", "❤️", "😂"
}
