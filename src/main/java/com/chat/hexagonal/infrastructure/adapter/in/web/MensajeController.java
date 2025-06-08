// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/MensajeController.java
package com.chat.hexagonal.infrastructure.adapter.in.web;

import com.chat.hexagonal.application.port.in.EnviarMensajeUseCase;
import com.chat.hexagonal.application.port.in.GestionarReaccionUseCase; // Importamos el nuevo caso de uso
import com.chat.hexagonal.application.port.in.BuscarMensajesUseCase; // Importamos el nuevo caso de uso
import com.chat.hexagonal.application.port.out.MensajeRepositoryPort; // Necesario para obtener mensajes
import com.chat.hexagonal.domain.model.Mensaje;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/mensajes") // Define la URL base para este controlador
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección
public class MensajeController {

    private final EnviarMensajeUseCase enviarMensajeUseCase;
    private final MensajeRepositoryPort mensajeRepositoryPort;
    private final GestionarReaccionUseCase gestionarReaccionUseCase;
    private final BuscarMensajesUseCase buscarMensajesUseCase; // Inyectamos el nuevo caso de uso

    /**
     * Endpoint para enviar un nuevo mensaje de texto.
     *
     * @param request El cuerpo de la solicitud con el contenido y el ID del remitente.
     * @return ResponseEntity con el mensaje enviado si es exitoso (HTTP 201 Created).
     */
    @PostMapping("/enviar/texto")
    public ResponseEntity<?> enviarMensajeTexto(@RequestBody EnviarMensajeTextoRequest request) {
        try {
            // Invoca el caso de uso para enviar el mensaje de texto
            Mensaje mensajeEnviado = enviarMensajeUseCase.enviarMensajeTexto(request.getContenido(), request.getIdRemitente());
            return ResponseEntity.status(HttpStatus.CREATED).body(mensajeEnviado);
        } catch (IllegalArgumentException e) {
            // Por ejemplo, si el remitente no se encuentra
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al enviar el mensaje de texto.");
        }
    }

    /**
     * Endpoint para enviar un nuevo mensaje de imagen.
     *
     * @param request El cuerpo de la solicitud con la URL de la imagen y el ID del remitente.
     * @return ResponseEntity con el mensaje enviado si es exitoso (HTTP 201 Created).
     */
    @PostMapping("/enviar/imagen")
    public ResponseEntity<?> enviarMensajeImagen(@RequestBody EnviarMensajeImagenRequest request) {
        try {
            // Invoca el caso de uso para enviar el mensaje de imagen
            Mensaje mensajeEnviado = enviarMensajeUseCase.enviarMensajeImagen(request.getImagenUrl(), request.getIdRemitente());
            return ResponseEntity.status(HttpStatus.CREATED).body(mensajeEnviado);
        } catch (IllegalArgumentException e) {
            // Por ejemplo, si el remitente no se encuentra o la URL de la imagen es inválida
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al enviar el mensaje de imagen.");
        }
    }

    /**
     * Endpoint para obtener los mensajes recientes del chat grupal.
     *
     * @param days El número de días hacia atrás desde hoy para obtener mensajes.
     * @return ResponseEntity con una lista de mensajes (HTTP 200 OK).
     */
    @GetMapping("/recientes")
    public ResponseEntity<List<Mensaje>> getMensajesRecientes(@RequestParam(defaultValue = "14") int days) {
        // Obtenemos los mensajes de las últimas 'days' semanas (por defecto 14 días = 2 semanas)
        LocalDateTime sinceDate = LocalDateTime.now().minusDays(days);
        List<Mensaje> mensajes = mensajeRepositoryPort.findRecentMessages(sinceDate);
        return ResponseEntity.ok(mensajes);
    }

    /**
     * Endpoint para agregar o remover una reacción a un mensaje.
     *
     * @param mensajeId El ID del mensaje al que se va a reaccionar (path variable).
     * @param request El cuerpo de la solicitud con el ID del usuario y el emoji.
     * @return ResponseEntity con el mensaje actualizado (HTTP 200 OK) o un mensaje de error.
     */
    @PostMapping("/{mensajeId}/reaccionar") // Mapea POST a /api/mensajes/{mensajeId}/reaccionar
    public ResponseEntity<?> reaccionarMensaje(@PathVariable String mensajeId, @RequestBody ReaccionarMensajeRequest request) {
        try {
            // Invoca el caso de uso para gestionar la reacción
            Mensaje mensajeActualizado = gestionarReaccionUseCase.toggleReaccion(mensajeId, request.getUserId(), request.getEmoji());
            return ResponseEntity.ok(mensajeActualizado);
        } catch (IllegalArgumentException e) {
            // Si el mensaje o usuario no se encuentran
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al reaccionar al mensaje.");
        }
    }

    /**
     * Endpoint para buscar mensajes por palabra clave y opcionalmente por usuario.
     *
     * @param keyword La palabra clave a buscar (requerido).
     * @param userId Opcional: El ID del usuario para filtrar los mensajes.
     * @return ResponseEntity con una lista de mensajes que coinciden con la búsqueda (HTTP 200 OK).
     */
    @GetMapping("/buscar") // Mapea las solicitudes GET a /api/mensajes/buscar
    public ResponseEntity<List<Mensaje>> buscarMensajes(
            @RequestParam String keyword, // Palabra clave, obligatoria
            @RequestParam(required = false) String userId) { // ID de usuario, opcional
        try {
            // Invoca el caso de uso para buscar mensajes
            List<Mensaje> resultados = buscarMensajesUseCase.buscarMensajes(keyword, userId);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            // Manejo genérico de errores durante la búsqueda
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // O un DTO de error si es más complejo
        }
    }

    // --- DTOs (Data Transfer Objects) para las solicitudes ---

    // DTO para la solicitud de envío de mensaje de texto
    // src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/EnviarMensajeTextoRequest.java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnviarMensajeTextoRequest {
        private String contenido;
        private String idRemitente;
    }

    // DTO para la solicitud de envío de mensaje de imagen
    // src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/EnviarMensajeImagenRequest.java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnviarMensajeImagenRequest {
        private String imagenUrl;
        private String idRemitente;
    }

        // --- DTO para la solicitud de reaccionar ---
    // src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/ReaccionarMensajeRequest.java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReaccionarMensajeRequest {
        private String userId;
        private String emoji;
    }
}
