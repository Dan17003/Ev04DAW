package pe.edu.tecsup.libros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.tecsup.libros.entity.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {}
