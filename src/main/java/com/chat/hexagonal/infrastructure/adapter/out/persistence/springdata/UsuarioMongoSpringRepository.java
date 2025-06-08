// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/springdata/UsuarioMongoSpringRepository.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence.springdata;

import com.chat.hexagonal.infrastructure.adapter.out.persistence.data.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marca esta interfaz como un componente de repositorio de Spring
public interface UsuarioMongoSpringRepository extends MongoRepository<UsuarioDocument, String> {

    // Spring Data MongoDB generará automáticamente la implementación de este método
    Optional<UsuarioDocument> findByNombreAnimal(String nombreAnimal);
}
