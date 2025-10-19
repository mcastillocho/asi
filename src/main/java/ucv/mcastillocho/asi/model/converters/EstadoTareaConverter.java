package ucv.mcastillocho.asi.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ucv.mcastillocho.asi.model.enums.EstadoTarea;

@Converter(autoApply = true)
public class EstadoTareaConverter implements AttributeConverter<EstadoTarea, Integer>{

    @Override
    public Integer convertToDatabaseColumn(EstadoTarea estadoTarea) {
        return estadoTarea != null ? estadoTarea.getValue() : null;
    }

    @Override
    public EstadoTarea convertToEntityAttribute(Integer id) {
        return id != null ? EstadoTarea.fromValue(id) : null;
    }
}
