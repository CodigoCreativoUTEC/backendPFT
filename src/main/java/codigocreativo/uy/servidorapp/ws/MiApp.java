package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.CORSFilter;
import codigocreativo.uy.servidorapp.jwt.JacksonConfig;
import codigocreativo.uy.servidorapp.jwt.JwtTokenFilter;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MiApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        
        // Recursos
        resources.add(UbicacionesResource.class);
        resources.add(ModeloResource.class);
        resources.add(IntervencionesResource.class);
        resources.add(TipoEquipoResource.class);
        resources.add(EquipoResource.class);
        resources.add(PaisesResource.class);
        resources.add(FuncionalidadResource.class);
        resources.add(PerfilResource.class);
        resources.add(MarcaResource.class);
        resources.add(UsuarioResource.class);
        resources.add(TipoIntervencionesResource.class);
        resources.add(ProveedoresResource.class);
        resources.add(OpenApiResource.class);
        
        // Filtros y configuraciones
        resources.add(JwtTokenFilter.class);
        resources.add(CORSFilter.class);
        resources.add(JacksonConfig.class);
        
        return resources;
    }
    
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        
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
        
        singletons.add(openAPI);
        return singletons;
    }
}
