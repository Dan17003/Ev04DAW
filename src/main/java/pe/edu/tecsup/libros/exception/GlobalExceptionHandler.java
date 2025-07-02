package pe.edu.tecsup.libros.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    // ¡Añade este nuevo manejador!
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        // Esto captura errores como violaciones de constraints NOT NULL o UNIQUE
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Error de integridad de datos");
        errorDetails.put("message", "La operación no se pudo completar. Verifique que los datos no violen las restricciones de la base de datos (p. ej. campos no nulos).");
        // Puedes hacer el mensaje más específico si analizas ex.getMessage()
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT); // 409 Conflict es una buena opción
    }
}