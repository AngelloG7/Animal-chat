// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/UsuarioController.java
package com.chat.hexagonal.infrastructure.adapter.in.web;

import com.chat.hexagonal.application.port.in.RegistrarUsuarioUseCase;
import com.chat.hexagonal.domain.exception.NombreAnimalDuplicadoException;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta clase es un controlador REST de Spring
@RequestMapping("/api/usuarios") // Define la URL base para todos los endpoints en este controlador
@RequiredArgsConstructor // Genera un constructor con los campos 'final' para inyección de dependencias
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    /**
     * Endpoint para registrar un nuevo usuario en el sistema.
     *
     * @param usuario El objeto Usuario recibido en el cuerpo de la solicitud (JSON).
     * @return ResponseEntity con el usuario registrado si es exitoso (HTTP 201 Created),
     * o un mensaje de error si el nombre del animal ya existe o la clave es inválida.
     */
    @PostMapping("/registrar") // Mapea las solicitudes POST a /api/usuarios/registrar
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Invoca el caso de uso de dominio para registrar el usuario
            Usuario usuarioRegistrado = registrarUsuarioUseCase.registrarUsuario(usuario);
            // Retorna el usuario registrado con un estado HTTP 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (NombreAnimalDuplicadoException e) {
            // Maneja el error de nombre de animal duplicado con HTTP 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Maneja errores de validación de argumentos con HTTP 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Manejo genérico de cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al registrar el usuario.");
        }
    }
}
