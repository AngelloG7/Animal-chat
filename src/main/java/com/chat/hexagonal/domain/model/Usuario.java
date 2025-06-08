// src/main/java/com/chat/hexagonal/domain/model/Usuario.java
package com.chat.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Genera getters, setters, toString, equals y hashCode
@AllArgsConstructor // Genera un constructor con todos los argumentos
@NoArgsConstructor // Genera un constructor sin argumentos
@Builder // Permite la construcción de objetos usando el patrón Builder
public class Usuario {
    private String id; // Identificador único generado por la base de datos
    private String nombreAnimal;
    private String icono; // URL o identificador del icono/avatar
    private String color; // Color asociado al animal
    private String clave; // Clave de 4 dígitos (se almacenará hasheada, no en texto plano)
    private LocalDateTime fechaRegistro; // Timestamp de cuando se registró el usuario
}
