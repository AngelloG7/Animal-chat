// src/main/java/com/chat/hexagonal/application/service/RegistrarUsuarioService.java
package com.chat.hexagonal.application.service;

import com.chat.hexagonal.application.port.in.RegistrarUsuarioUseCase;
import com.chat.hexagonal.application.port.out.UsuarioRepositoryPort;
import com.chat.hexagonal.domain.exception.NombreAnimalDuplicadoException;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // Marca esta clase como un servicio de Spring
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección de dependencias
public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoder passwordEncoder; // Inyectamos PasswordEncoder

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // 1. Validar unicidad del nombre de animal
        if (usuarioRepositoryPort.findByNombreAnimal(usuario.getNombreAnimal()).isPresent()) {
            throw new NombreAnimalDuplicadoException("El nombre de animal '" + usuario.getNombreAnimal() + "' ya está en uso.");
        }

        // 2. Validar la longitud de la clave (Requerimiento: 4 dígitos)
        if (usuario.getClave() == null || usuario.getClave().length() != 4 || !usuario.getClave().matches("\\d{4}")) {
            throw new IllegalArgumentException("La clave debe ser de 4 dígitos numéricos.");
        }

        // 3. Hashear la clave (¡Crucial para la seguridad!)
        String claveHasheada = passwordEncoder.encode(usuario.getClave());
        usuario.setClave(claveHasheada);

        // 4. Establecer la fecha de registro
        usuario.setFechaRegistro(LocalDateTime.now());

        // 5. Guardar el usuario usando el puerto de persistencia
        return usuarioRepositoryPort.save(usuario);
    }
}
