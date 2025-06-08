// src/main/java/com/chat/hexagonal/application/port/in/RegistrarUsuarioUseCase.java
package com.chat.hexagonal.application.port.in;

import com.chat.hexagonal.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {

    /**
     * Registra un nuevo usuario en el sistema.
     * @param usuario El objeto Usuario con los datos para el registro.
     * (Nota: El ID y la fecha de registro se establecerán internamente).
     * @return El Usuario registrado exitosamente.
     * @throws com.chat.hexagonal.domain.exception.NombreAnimalDuplicadoException si el nombre del animal ya existe.
     * @throws IllegalArgumentException si la clave no tiene 4 dígitos.
     */
    Usuario registrarUsuario(Usuario usuario);
}
