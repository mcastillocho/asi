package ucv.mcastillocho.asi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import ucv.mcastillocho.asi.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        // Conectar al encoder
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SessionInformationExpiredStrategy expiredSessionStrategy() {
        return new SessionInformationExpiredStrategy() {
            @Override
            public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
                event.getRequest().getSession().setAttribute("sessionExpiredMessage",
                        "Tu sesión ha expirado debido a un nuevo inicio de sesión.");
                event.getResponse().sendRedirect("/login");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                // Recursos estáticos públicos
                .requestMatchers("/css/**", "/js/**").permitAll()

                // Página de login y registro pública
                .requestMatchers("/login", "/register").permitAll()
                

                // Página principal solo para usuarios logueados
                .requestMatchers("/").hasAnyRole("USUARIO")

                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .usernameParameter("user")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureHandler((request, response, exception) -> {
                            // Usar flash attributes en lugar de parámetros URL
                            request.getSession().setAttribute("loginError", "Usuario o contraseña incorrectos.");
                            response.sendRedirect("/login");
                        })
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Usar flash attributes en lugar de parámetros URL
                            request.getSession().setAttribute("logoutMessage", "Has cerrado sesión correctamente.");
                            response.sendRedirect("/login");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredSessionStrategy(expiredSessionStrategy()))

                // Deshabilitar CSRF para todas las APIs
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**"));

        return http.build();
    }
}
