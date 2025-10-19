package ucv.mcastillocho.asi.model.entities;

import java.time.LocalDateTime;

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
import ucv.mcastillocho.asi.model.converters.EstadoTareaConverter;
import ucv.mcastillocho.asi.model.enums.EstadoTarea;

@Data
@Entity
@Table(name = "tarea")
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre_tarea", nullable = false)
    private String nombreTarea;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Convert(converter = EstadoTareaConverter.class)
    @Column(name = "id_estado", nullable = false)
    private EstadoTarea estadoTarea;

    @Column(nullable = false)
    private int prioridad;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable", nullable = false)
    private Usuario responsable;

    // Métodos helper para obtener IDs
    public int getIdEquipo() {
        return equipo != null ? equipo.getId() : 0;
    }

    public int getIdResponsable() {
        return responsable != null ? responsable.getId() : 0;
    }
}
