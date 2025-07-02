package pe.edu.tecsup.libros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; // ¡Importa esto!
import pe.edu.tecsup.libros.entity.Autor;
import pe.edu.tecsup.libros.entity.Libro;
import pe.edu.tecsup.libros.repository.AutorRepository; // ¡Importa el repo de autor!
import pe.edu.tecsup.libros.repository.LibroRepository;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository; // ¡Inyecta el repositorio de Autor!

    @GetMapping
    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    // --- MÉTODO 'crear' CORREGIDO ---
    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        // 1. Valida que el autor y su ID no sean nulos
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del autor es obligatorio.");
        }

        // 2. Busca el autor en la base de datos por su ID
        Autor autor = autorRepository.findById(libro.getAutor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado con el ID: " + libro.getAutor().getId()));

        // 3. Asigna la entidad Autor completa y manejada por JPA al libro
        libro.setAutor(autor);

        // 4. Guarda el libro con la referencia correcta
        return libroRepository.save(libro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MÉTODO 'actualizar' TAMBIÉN CORREGIDO ---
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        // Valida que el autor y su ID no sean nulos en la petición de actualización
        if (libroActualizado.getAutor() == null || libroActualizado.getAutor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del autor es obligatorio.");
        }

        // Busca el autor que se quiere asignar
        Autor autor = autorRepository.findById(libroActualizado.getAutor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado con el ID: " + libroActualizado.getAutor().getId()));

        // Busca el libro existente que se quiere actualizar
        return libroRepository.findById(id)
                .map(libroExistente -> {
                    libroExistente.setTitulo(libroActualizado.getTitulo());
                    libroExistente.setAnio(libroActualizado.getAnio());
                    libroExistente.setAutor(autor); // Asigna la entidad Autor completa
                    return ResponseEntity.ok(libroRepository.save(libroExistente));
                }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return libroRepository.findById(id)
                .map(p -> {
                    libroRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}