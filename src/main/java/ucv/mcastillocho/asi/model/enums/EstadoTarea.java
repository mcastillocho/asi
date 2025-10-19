package ucv.mcastillocho.asi.model.enums;

public enum EstadoTarea {
    
    PENDIENTE, // Tareas que aun no inicia su plazo
    EN_CURSO, // Tareas que iniciaron su periodo
    FINALIZADO, // Tareas que se finalizaron correctamente
    CANCELADO; // Tareas que se cancelaron

    private static EstadoTarea[] VALUES_BY_ID = EstadoTarea.values();
   
    public static EstadoTarea fromValue(int valor) {
        if (valor < 1 || valor > 2) {
            throw new IllegalArgumentException("Valor de EstadoTarea indicado es inválido: " + valor);
        }
        return VALUES_BY_ID[valor - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }
}
