package pe.edu.tecsup.libros.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // ¡Paso CRÍTICO! Habilita la configuración de CORS de abajo
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Estas rutas no necesitan autenticación de ningún tipo
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Las peticiones GET a libros y autores son públicas
                        .requestMatchers(HttpMethod.GET, "/api/libros/**", "/api/autores/**").permitAll()
                        // Cualquier otra petición (POST, PUT, DELETE) requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()) // Habilita la Autenticación Básica (usuario y contraseña)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sin sesiones en el servidor

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // ¡IMPORTANTE! Añade la URL de tu futuro frontend.
        // El comodín '*' permite que cualquier subdominio en onrender.com se conecte.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://*.onrender.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var uds = new InMemoryUserDetailsManager();
        // Creamos un usuario en memoria para las pruebas
        var user = User.withUsername("admin")
                .password(encoder.encode("admin")) // La contraseña es "admin"
                .roles("ADMIN")
                .build();
        uds.createUser(user);
        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}