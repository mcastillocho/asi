package ucv.mcastillocho.asi.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ucv.mcastillocho.asi.model.converters.RolEquipoConverter;
import ucv.mcastillocho.asi.model.enums.RolEquipo;

@Data
@Entity
@Table(name = "miembro_equipo")
@NoArgsConstructor
@AllArgsConstructor
public class MiembroEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @Convert(converter = RolEquipoConverter.class)
    @Column(name = "id_rol", nullable = false)
    private RolEquipo rolEquipo;

    // Métodos helper para obtener IDs
    public int getIdUsuario() {
        return usuario != null ? usuario.getId() : 0;
    }

    public int getIdEquipo() {
        return equipo != null ? equipo.getId() : 0;
    }
}
