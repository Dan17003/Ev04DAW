package pe.edu.tecsup.libros.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.tecsup.libros.entity.Autor;
import pe.edu.tecsup.libros.repository.AutorRepository;


import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired private AutorRepository repo;

    @PostMapping
    public String crear(@RequestBody Autor a) {
        repo.save(a);
        return "Autor creado";
    }

    @GetMapping
    public List<Autor> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Autor obtener(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody Autor actualizado) {
        return repo.findById(id).map(a -> {
            a.setNombre(actualizado.getNombre());
            repo.save(a);
            return "Actualizado";
        }).orElse("No encontrado");
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Eliminado";
        }
        return "No encontrado";
    }
}
