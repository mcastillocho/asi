package ucv.mcastillocho.asi.model.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    // DATOS DEL ESTUDIANTE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String nombre;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String apellido;

    // INFORMACION PARA LOGIN

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'USUARIO'", nullable = false)
    private String rol = "USUARIO";

    @Column(nullable = false)
    private String pass;

    // INFORMACION ESTADISTICA

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean activo = true;

    // RELACIONES JPA

    @OneToMany(mappedBy = "lider", fetch = FetchType.LAZY)
    private List<Equipo> equiposLiderados;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<MiembroEquipo> membresias;

    // NUEVA RELACIÓN CON TAREAS ASIGNADAS
    @OneToMany(mappedBy = "responsable", fetch = FetchType.LAZY)
    private List<Tarea> tareasAsignadas;

    // CONFIGURACION SPRING SECURITY

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por el momento todos tienen el rol usuario
        return Collections.singletonList(new SimpleGrantedAuthority("ROL_" + rol));
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
