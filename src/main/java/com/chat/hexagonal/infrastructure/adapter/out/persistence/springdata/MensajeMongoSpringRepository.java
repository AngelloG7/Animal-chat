// src/main/java/com/chat/hexagonal/infrastructure/adapter/out/persistence/springdata/MensajeMongoSpringRepository.java
package com.chat.hexagonal.infrastructure.adapter.out.persistence.springdata;

import com.chat.hexagonal.infrastructure.adapter.out.persistence.data.MensajeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeMongoSpringRepository extends MongoRepository<MensajeDocument, String> {

    // Buscar mensajes más recientes que una fecha dada, ordenados por fecha de envío descendente
    List<MensajeDocument> findByFechaEnvioAfterOrderByFechaEnvioDesc(LocalDateTime fecha);

    // Buscar mensajes por contenido o nombre de remitente, o por ID de remitente
    // Este método es flexible para la búsqueda por palabra clave.
    @Query("{ $or: [ { 'contenido' : { $regex : ?0, $options: 'i' } }, { 'nombreRemitente' : { $regex : ?0, $options: 'i' } } ], $and: [ { 'idRemitente' : ?1 } ] }")
    List<MensajeDocument> findByContenidoRegexOrNombreRemitenteRegexAndIdRemitente(String keywordRegex, String idRemitente);

    // Búsqueda solo por palabra clave (contenido o nombre de remitente)
    List<MensajeDocument> findByContenidoRegexOrNombreRemitenteRegex(String keywordRegex);
}
