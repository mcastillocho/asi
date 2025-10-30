package ucv.mcastillocho.asi.dto;

import lombok.Data;

@Data
public class RegistroDTO {

    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String confirmarPassword;
}