package ucv.mcastillocho.asi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ucv.mcastillocho.asi.model.entities.MiembroEquipo;

@Repository
public interface MiembroEquipoRepository extends JpaRepository<MiembroEquipo, Integer> {

    /**
     * Busca todos los miembros de un equipo y su rol.
     */
    List<MiembroEquipo> findByEquipoId(int equipoId);

    /**
     * Obtiene los grupos a los que pertenece un asesor.
     */
    List<MiembroEquipo> findByUsuarioId(int usuarioId);

    /**
     * Verifica si un usuario ya es miembro de un equipo.
     */
    boolean existsByUsuarioIdAndEquipoId(int usuarioId, int equipoId);
}