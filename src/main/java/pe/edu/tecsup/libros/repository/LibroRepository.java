package pe.edu.tecsup.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.tecsup.libros.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {}
