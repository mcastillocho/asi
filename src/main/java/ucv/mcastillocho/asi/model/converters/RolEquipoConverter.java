package ucv.mcastillocho.asi.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ucv.mcastillocho.asi.model.enums.RolEquipo;

@Converter(autoApply = true)
public class RolEquipoConverter implements AttributeConverter<RolEquipo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(RolEquipo rolEquipo) {
        return rolEquipo != null ? rolEquipo.getValue() : null;
    }

    @Override
    public RolEquipo convertToEntityAttribute(Integer id) {
        return id != null ? RolEquipo.fromValue(id) : null;
    }
    
}
