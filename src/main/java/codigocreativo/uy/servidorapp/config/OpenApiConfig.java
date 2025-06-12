package codigocreativo.uy.servidorapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OpenApiConfig extends Application {
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("http://codigocreativo.ddns.net:8080/ServidorApp-1.0-SNAPSHOT/api"))
            .info(new Info()
                .title("API de Gestión de Equipos")
                .description("API para la gestión de equipos, intervenciones y mantenimientos")
                .version("1.0")
                .contact(new Contact()
                    .name("Código Creativo")
                    .email("contacto@codigocreativo.uy")
                    .url("https://codigocreativo.uy"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
            .schemaRequirement("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT token de autenticación"));
    }
}