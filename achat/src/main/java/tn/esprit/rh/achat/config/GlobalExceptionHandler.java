package tn.esprit.rh.achat.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Ressource introuvable (ex: produit ID inconnu)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Ressource introuvable");
    }

    // Argument illégal
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Requête invalide");
    }

    // Route inexistante
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandler(NoHandlerFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Endpoint introuvable");
    }

    // Toute autre erreur inattendue — ne jamais exposer le détail
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        // Log interne uniquement — pas exposé au client
        System.err.println("[ERREUR INTERNE] " + ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("message", message);
        // Pas de stack trace, pas de détail technique
        return ResponseEntity.status(status).body(body);
    }
}