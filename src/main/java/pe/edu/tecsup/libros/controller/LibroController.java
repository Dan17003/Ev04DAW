package pe.edu.tecsup.libros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.tecsup.libros.entity.Libro;
import pe.edu.tecsup.libros.repository.LibroRepository;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    @Autowired
    private LibroRepository repo;

    @PostMapping
    public Libro crear(@RequestBody Libro libro) {
        return repo.save(libro);
    }

    @GetMapping
    public List<Libro> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(@PathVariable Long id, @RequestBody Libro l) {
        return repo.findById(id)
                .map(lib -> {
                    lib.setTitulo(l.getTitulo());
                    lib.setAnio(l.getAnio());
                    lib.setAutor(l.getAutor());
                    return ResponseEntity.ok(repo.save(lib));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return repo.findById(id)
                .map(p -> {
                    repo.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
