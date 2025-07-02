package pe.edu.tecsup.libros.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
// ¡ELIMINA LA ANOTACIÓN @JsonIdentityInfo DE AQUÍ!
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @Min(1500)
    private int anio;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    @NotNull
    private Autor autor; // No necesita ninguna anotación especial

    // Getters y Setters no cambian...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}