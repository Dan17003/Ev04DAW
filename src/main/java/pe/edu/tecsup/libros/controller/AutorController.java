package pe.edu.tecsup.libros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.tecsup.libros.entity.Autor;
import pe.edu.tecsup.libros.repository.AutorRepository;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired
    private AutorRepository repo;

    @GetMapping
    public List<Autor> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Autor crear(@RequestBody Autor a) {
        return repo.save(a);
    }

    @GetMapping("/{id}")
    public Autor obtener(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
    }

    @PutMapping("/{id}")
    public Autor actualizar(@PathVariable Long id, @RequestBody Autor autorActualizado) {
        return repo.findById(id).map(a -> {
            a.setNombre(autorActualizado.getNombre());
            return repo.save(a);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Autor autor = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));

        // Lógica de negocio: No permitir borrar si tiene libros asociados
        if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar el autor porque tiene libros asociados.");
        }

        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}