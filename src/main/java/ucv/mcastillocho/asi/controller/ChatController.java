package ucv.mcastillocho.asi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ucv.mcastillocho.asi.service.ChatService;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Endpoint de prueba para chatear con la IA.
     * * @param mensaje El prompt para enviar a la IA.
     * 
     * @return La respuesta de la IA.
     */
    @GetMapping("/probar-chat")
    public String probarChat(
            @RequestParam(value = "mensaje", defaultValue = "Ayudame con la delegación de tareas para mi equipo de trabajo") String mensaje) {

        return chatService.generarRespuesta(mensaje);
    }
}