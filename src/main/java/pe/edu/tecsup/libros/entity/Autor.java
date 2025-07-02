package pe.edu.tecsup.libros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- ¡Importa esto!
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
// ¡ELIMINA LA ANOTACIÓN @JsonIdentityInfo DE AQUÍ!
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore // <-- ¡AÑADE ESTA ANOTACIÓN AQUÍ!
    private List<Libro> libros;

    // Getters y Setters no cambian...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}