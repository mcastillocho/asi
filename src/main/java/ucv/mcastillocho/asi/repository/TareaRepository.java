package ucv.mcastillocho.asi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ucv.mcastillocho.asi.model.entities.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    /**
     * Obtiene todas las tareas creadas en un grupo.
     */
    List<Tarea> findByEquipoId(int equipoId);

    /**
     * Obtiene todas las tareas asignadas a un asesor específico.
     */
    List<Tarea> findByResponsableIdAndEquipoId(int responsableId, int equipoId);
}