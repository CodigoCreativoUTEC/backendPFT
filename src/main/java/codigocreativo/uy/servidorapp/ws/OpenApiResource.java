package codigocreativo.uy.servidorapp.ws;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/openapi")
public class OpenApiResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOpenApi() {
        OpenAPI openAPI = new OpenAPI()
            .info(new Info()
                .title("API de Gestión de Equipos")
                .description("API para la gestión de equipos, intervenciones y usuarios")
                .version("1.0"))
            .schemaRequirement("bearerAuth", 
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"));
        
        return Response.ok(openAPI).build();
    }
} 