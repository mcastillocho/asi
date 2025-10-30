package ucv.mcastillocho.asi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ucv.mcastillocho.asi.model.entities.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {

    /**
     * Obtener los grupos a los que pertenece un asesor.
     */
    @Query("SELECT e FROM Equipo e JOIN e.miembros m WHERE m.usuario.id = :usuarioId")
    List<Equipo> findEquiposByUsuarioId(@Param("usuarioId") int usuarioId);
}