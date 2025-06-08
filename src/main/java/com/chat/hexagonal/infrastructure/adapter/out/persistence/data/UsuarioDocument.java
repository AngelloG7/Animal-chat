// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/data/UsuarioDocument.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "usuarios") // Anotación de Spring Data para mapear a una colección de MongoDB
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDocument {

    @Id // Marca este campo como el ID principal del documento en MongoDB
    private String id;

    @Indexed(unique = true) // Crea un índice único en este campo a nivel de MongoDB
    private String nombreAnimal;
    private String icono;
    private String color;
    private String clave; // Almacenará la clave hasheada
    private LocalDateTime fechaRegistro;
}
