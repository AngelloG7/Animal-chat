// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/UsuarioMongoAdapter.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence;

import com.chat.hexagonal.application.port.out.UsuarioRepositoryPort;
import com.chat.hexagonal.domain.model.Usuario;
import com.chat.hexagonal.infrastructure.adapter.out.persistence.data.UsuarioDocument;
import com.chat.hexagonal.infrastructure.adapter.out.persistence.springdata.UsuarioMongoSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // Marca esta clase como un componente de Spring para que pueda ser inyectada
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección de dependencias
public class UsuarioMongoAdapter implements UsuarioRepositoryPort {

    private final UsuarioMongoSpringRepository usuarioMongoSpringRepository;

    @Override
    public Usuario save(Usuario usuario) {
        // Convierte la entidad de dominio a un documento de MongoDB
        UsuarioDocument usuarioDocument = toUsuarioDocument(usuario);
        UsuarioDocument savedDocument = usuarioMongoSpringRepository.save(usuarioDocument);
        // Convierte el documento guardado de vuelta a una entidad de dominio
        return toUsuario(savedDocument);
    }

    @Override
    public Optional<Usuario> findByNombreAnimal(String nombreAnimal) {
        // Busca el documento y lo convierte a una entidad de dominio si existe
        return usuarioMongoSpringRepository.findByNombreAnimal(nombreAnimal)
                .map(this::toUsuario); // Utiliza el método de conversión
    }

    @Override
    public Optional<Usuario> findById(String id) {
        // Busca el documento por ID y lo convierte a una entidad de dominio
        return usuarioMongoSpringRepository.findById(id)
                .map(this::toUsuario);
    }

    // --- Métodos de Conversión (Mappers) ---

    // Convierte un Usuario de Dominio a un UsuarioDocument para la base de datos
    private UsuarioDocument toUsuarioDocument(Usuario usuario) {
        return UsuarioDocument.builder()
                .id(usuario.getId()) // El ID podría ser nulo si es un nuevo usuario
                .nombreAnimal(usuario.getNombreAnimal())
                .icono(usuario.getIcono())
                .color(usuario.getColor())
                .clave(usuario.getClave()) // Recuerda, esta clave debe ser hasheada ANTES de llegar aquí
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }

    // Convierte un UsuarioDocument de la base de datos a un Usuario de Dominio
    private Usuario toUsuario(UsuarioDocument document) {
        return Usuario.builder()
                .id(document.getId())
                .nombreAnimal(document.getNombreAnimal())
                .icono(document.getIcono())
                .color(document.getColor())
                .clave(document.getClave())
                .fechaRegistro(document.getFechaRegistro())
                .build();
    }
}
