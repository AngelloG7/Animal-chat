// src/main/java/com.chat/hexagonal/application/service/IngresarChatService.java
package com.chat.hexagonal.application.service;

import com.chat.hexagonal.application.port.in.IngresarChatUseCase;
import com.chat.hexagonal.application.port.out.UsuarioRepositoryPort;
import com.chat.hexagonal.domain.exception.CredencialesInvalidasException;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un servicio de Spring
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección de dependencias
public class IngresarChatService implements IngresarChatUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder; // Inyectamos PasswordEncoder

    @Override
    public Usuario ingresarChat(String nombreAnimal, String clave) {
        // 1. Buscar el usuario por nombre de animal
        Usuario usuario = usuarioRepositoryPort.findByNombreAnimal(nombreAnimal)
                .orElseThrow(() -> new CredencialesInvalidasException("Nombre de animal o clave incorrectos."));

        // 2. Comparar la clave proporcionada con la clave hasheada almacenada
        // ¡Nunca compares claves en texto plano! Usa passwordEncoder.matches()
        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new CredencialesInvalidasException("Nombre de animal o clave incorrectos.");
        }

        // Si la clave coincide, el usuario ha ingresado exitosamente
        return usuario;
    }
}
