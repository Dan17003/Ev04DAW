package pe.edu.tecsup.libros;

import pe.edu.tecsup.libros.entity.Rol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.tecsup.libros.entity.Usuario;
import pe.edu.tecsup.libros.repository.UsuarioRepository;

@SpringBootApplication
public class Ev04DawApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ev04DawApplication.class, args);
    }

    @Bean
    CommandLineRunner initUser(UsuarioRepository usuarioRepo, PasswordEncoder encoder) {
        return args -> {
            if (usuarioRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin")); // encripta la clave
                admin.setRol(Rol.ADMIN); // asegúrate que Rol.ADMIN existe
                usuarioRepo.save(admin);
                System.out.println("✅ Usuario admin creado");
            }
        };
    }

}
