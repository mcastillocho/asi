package ucv.mcastillocho.asi.model.enums;

public enum RolEquipo {

    LIDER,
    ADMINISTRADOR,
    MIEMBRO;

    private static final RolEquipo[] VALUES_BY_ID = RolEquipo.values();

    public static RolEquipo fromValue(int valor) {
        if (valor < 1 || valor > 2) {
            throw new IllegalArgumentException("Valor de RolEquipo indicado es inválido: " + valor);
        }
        return VALUES_BY_ID[valor - 1];
    }

    public int getValue() {
        return ordinal() + 1;
    }
}
