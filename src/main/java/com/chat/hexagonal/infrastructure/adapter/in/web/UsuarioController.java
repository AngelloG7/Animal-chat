// src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/UsuarioController.java
package com.chat.hexagonal.infrastructure.adapter.in.web;

import com.chat.hexagonal.application.port.in.IngresarChatUseCase; // Importamos el nuevo caso de uso
import com.chat.hexagonal.application.port.in.RegistrarUsuarioUseCase;
import com.chat.hexagonal.domain.exception.CredencialesInvalidasException; // Importamos la nueva excepción
import com.chat.hexagonal.domain.exception.NombreAnimalDuplicadoException;
import com.chat.hexagonal.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final IngresarChatUseCase ingresarChatUseCase; // Inyectamos el nuevo caso de uso

    /**
     * Endpoint para registrar un nuevo usuario en el sistema.
     *
     * @param usuario El objeto Usuario recibido en el cuerpo de la solicitud (JSON).
     * @return ResponseEntity con el usuario registrado si es exitoso (HTTP 201 Created),
     * o un mensaje de error si el nombre del animal ya existe o la clave es inválida.
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = registrarUsuarioUseCase.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (NombreAnimalDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al registrar el usuario.");
        }
    }

    /**
     * Endpoint para que un usuario ingrese al chat.
     *
     * @param request Un objeto que contiene el nombreAnimal y la clave del usuario.
     * Se utiliza un Map o un DTO simple para recibir ambos campos.
     * @return ResponseEntity con los datos del usuario si el ingreso es exitoso (HTTP 200 OK),
     * o un mensaje de error si las credenciales son inválidas (HTTP 401 Unauthorized).
     */
    @PostMapping("/login") // Mapea las solicitudes POST a /api/usuarios/login
    public ResponseEntity<?> loginUsuario(@RequestBody LoginRequest request) { // Usamos un DTO simple
        try {
            // Invoca el caso de uso de dominio para intentar el ingreso
            Usuario usuarioLogeado = ingresarChatUseCase.ingresarChat(request.getNombreAnimal(), request.getClave());
            // Retorna los datos del usuario (sin la clave hasheada) con un estado HTTP 200 OK
            // Considera crear un DTO de respuesta para no exponer la clave hasheada
            usuarioLogeado.setClave(null); // No enviamos la clave hasheada al cliente
            return ResponseEntity.ok(usuarioLogeado);
        } catch (CredencialesInvalidasException e) {
            // Maneja credenciales inválidas con HTTP 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Manejo genérico de cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado al intentar ingresar.");
        }
    }

    // DTO (Data Transfer Object) para la solicitud de login
    // Esta clase debería crearse en un paquete separado, por ejemplo:
    // src/main/java/com/chat/hexagonal/infrastructure/adapter/in/web/dto/LoginRequest.java
    @Data // Lombok para getters, setters
    @NoArgsConstructor // Constructor sin argumentos
    @AllArgsConstructor // Constructor con todos los argumentos
    public static class LoginRequest {
        private String nombreAnimal;
        private String clave;
    }
}
