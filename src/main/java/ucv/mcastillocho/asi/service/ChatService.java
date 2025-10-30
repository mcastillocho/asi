package ucv.mcastillocho.asi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        log.info("Construyendo el ChatClient desde el Builder...");

        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Envía un prompt a la API de OpenAI y devuelve la respuesta.
     * 
     * @param mensaje El prompt del usuario.
     * @return La respuesta generada por el modelo.
     */
    public String generarRespuesta(String mensaje) {
        log.info("Enviando prompt a OpenAI: '{}'", mensaje);

        try {
            // El resto de tu código funciona exactamente igual
            String respuesta = chatClient.prompt()
                    .user(mensaje)
                    .call()
                    .content();

            log.info("Respuesta recibida de OpenAI.");
            return respuesta;

        } catch (Exception e) {
            log.error("Error al llamar a la API de OpenAI", e);
            return "Error: No se pudo conectar o procesar la respuesta de la API. Revisa los logs.";
        }
    }
}