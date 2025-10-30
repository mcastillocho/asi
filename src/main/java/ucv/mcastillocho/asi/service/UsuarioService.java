package ucv.mcastillocho.asi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ucv.mcastillocho.asi.dto.RegistroDTO;
import ucv.mcastillocho.asi.model.entities.Usuario;
import ucv.mcastillocho.asi.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public Usuario registrarNuevoUsuario(RegistroDTO registroDTO) throws Exception {

        // Valida que las contraseñas coincidan
        if (!registroDTO.getPassword().equals(registroDTO.getConfirmarPassword())) {
            throw new Exception("Las contraseñas no coinciden.");
        }

        // Valida que no haya un usuario con ese correo registrado
        if (usuarioRepository.findByCorreoAndEstadoIsTrue(registroDTO.getCorreo()).isPresent()) {
            throw new Exception("Ya existe un usuario con el correo: " + registroDTO.getCorreo());
        }

        // Crea la nueva entidad Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setApellido(registroDTO.getApellido());
        nuevoUsuario.setCorreo(registroDTO.getCorreo());

        // Cifra la contraseña (usa BCrypt)
        nuevoUsuario.setPass(passwordEncoder.encode(registroDTO.getPassword()));

        // Establecer valores por defecto del rol y estado
        nuevoUsuario.setRol("USUARIO");
        nuevoUsuario.setEstado(true);

        // Guarda en la base de datos
        return usuarioRepository.save(nuevoUsuario);
    }
}