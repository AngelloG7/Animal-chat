// src/main/java/com/chat/hexagonal/application/port/out/UsuarioRepositoryPort.java
package com.chat.hexagonal.application.port.out;

import com.chat.hexagonal.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param usuario El objeto Usuario a guardar.
     * @return El Usuario guardado, que podría incluir un ID generado.
     */
    Usuario save(Usuario usuario);

    /**
     * Busca un usuario por su nombre de animal.
     * @param nombreAnimal El nombre del animal del usuario a buscar.
     * @return Un Optional que contiene el Usuario si se encuentra, o un Optional vacío si no.
     */
    Optional<Usuario> findByNombreAnimal(String nombreAnimal);

    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario a buscar.
     * @return Un Optional que contiene el Usuario si se encuentra, o un Optional vacío si no.
     */
    Optional<Usuario> findById(String id);

    // Más operaciones de persistencia pueden ser añadidas aquí según sea necesario.
}
