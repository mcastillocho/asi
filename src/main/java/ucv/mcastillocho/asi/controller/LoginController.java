package ucv.mcastillocho.asi.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import ucv.mcastillocho.asi.dto.RegistroDTO;
import ucv.mcastillocho.asi.service.UsuarioService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;

    /**
     * Muestra la página de login/registro
     */
    @GetMapping("/login")
    public String login(Model model, Authentication authentication, HttpServletRequest request) {
        // Si ya está autenticado
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        // Envía y lee los datos de flash attributes

        // Añade el DTO vacío para el formulario de registro (th:object)
        model.addAttribute("registroDTO", new RegistroDTO());

        // Verifica si hay un mensaje de error para mostrarlo
        String error = (String) request.getSession().getAttribute("loginError");
        if (error != null) {
            model.addAttribute("error", error);
            request.getSession().removeAttribute("loginError");
        }

        String sessionExpireMessage = (String) request.getSession().getAttribute("sessionExpiredMessage");
        if (sessionExpireMessage != null) {
            // Lo envia como 'error' para que se muestre
            model.addAttribute("error", sessionExpireMessage);
            request.getSession().removeAttribute("sessionExpireMessage");
        }

        // Muestra mensajes de confirmacion
        String logoutMessage = (String) request.getSession().getAttribute("logoutMessage");
        if (logoutMessage != null) {
            // Envia el logout como "success"
            model.addAttribute("success", logoutMessage);
            request.getSession().removeAttribute("logoutMessage");
        }

        String registroExitoso = (String) request.getSession().getAttribute("registroExitoso");
        if (registroExitoso != null) {
            // Envía el register como "success"
            model.addAttribute("success", registroExitoso);
            request.getSession().removeAttribute("registroExitoso");
        }

        return "login";
    }

    /**
     * Procesa el formulario de registro
     */
    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("registroDTO") RegistroDTO registroDTO,
            HttpServletRequest request) {

        try {
            usuarioService.registrarNuevoUsuario(registroDTO);

            // Si el registro es exitoso, envía confirmación omo respuesta
            request.getSession().setAttribute("registroExitoso", "¡Cuenta creada! Ya puedes iniciar sesión.");

        } catch (Exception e) {
            // Si falla, informa como un error de login
            request.getSession().setAttribute("loginError", e.getMessage());
        }

        // Envía a login para confirmar el registro
        return "redirect:/login";
    }
}