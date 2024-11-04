package br.com.fiap;

import br.com.fiap.filters.CORSFilter;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api") // Altere o caminho base conforme necessário
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        packages("br.com.fiap.resource"); // Certifique-se de que isso inclui seus recursos
        register(CORSFilter.class); // Registre o filtro CORS aqui
    }
}
