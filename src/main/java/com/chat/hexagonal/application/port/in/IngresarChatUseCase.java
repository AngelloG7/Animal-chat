// src/main/java/com/chat/hexagonal/application/port/in/IngresarChatUseCase.java
package com.chat.hexagonal.application.port.in;

import com.chat.hexagonal.domain.model.Usuario;

public interface IngresarChatUseCase {

    /**
     * Permite que un usuario ingrese al chat validando sus credenciales.
     * @param nombreAnimal El nombre de animal del usuario.
     * @param clave La clave de 4 dígitos proporcionada por el usuario.
     * @return El objeto Usuario si las credenciales son válidas.
     * @throws com.chat.hexagonal.domain.exception.CredencialesInvalidasException si el nombre de animal o la clave son incorrectos.
     */
    Usuario ingresarChat(String nombreAnimal, String clave);
}
