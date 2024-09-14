package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.CORSFilter;
import codigocreativo.uy.servidorapp.jwt.JacksonConfig;
import codigocreativo.uy.servidorapp.jwt.JwtTokenFilter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MiApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(CORSFilter.class);
        resources.add(JacksonConfig.class);
        resources.add(JwtTokenFilter.class);

        resources.add(UsuarioResource.class);
        resources.add(EquipoResource.class);
        resources.add(ProveedoresResource.class);
        resources.add(MarcaResource.class);
        resources.add(ModeloResource.class);
        return resources;
    }
}
