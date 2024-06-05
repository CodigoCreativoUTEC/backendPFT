package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.CORSFilter;
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
        //resources.add(ProductoResource.class);
        resources.add(EquipoResource.class);
        return resources;
    }

}
