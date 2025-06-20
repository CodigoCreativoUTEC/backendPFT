package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.filtros.AuditoriaFilter;
import codigocreativo.uy.servidorapp.filtros.CORSFilter;
import codigocreativo.uy.servidorapp.filtros.JwtTokenFilter;
import codigocreativo.uy.servidorapp.config.JacksonConfig;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class MiAppTest {
    
    @Test
    void testGetClassesContainsAllResources() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();
        
        // Verificar que contiene todos los recursos esperados
        assertTrue(classes.contains(UbicacionesResource.class));
        assertTrue(classes.contains(ModeloResource.class));
        assertTrue(classes.contains(IntervencionesResource.class));
        assertTrue(classes.contains(TipoEquipoResource.class));
        assertTrue(classes.contains(EquipoResource.class));
        assertTrue(classes.contains(PaisesResource.class));
        assertTrue(classes.contains(FuncionalidadResource.class));
        assertTrue(classes.contains(PerfilResource.class));
        assertTrue(classes.contains(MarcaResource.class));
        assertTrue(classes.contains(UsuarioResource.class));
        assertTrue(classes.contains(TipoIntervencionesResource.class));
        assertTrue(classes.contains(ProveedoresResource.class));
    }
    
    @Test
    void testGetClassesContainsAllFiltersAndConfig() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();
        
        // Verificar que contiene todos los filtros y configuraciones
        assertTrue(classes.contains(AuditoriaFilter.class));
        assertTrue(classes.contains(JwtTokenFilter.class));
        assertTrue(classes.contains(CORSFilter.class));
        assertTrue(classes.contains(JacksonConfig.class));
    }
    
    @Test
    void testGetClassesContainsOpenApi() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();
        
        // Verificar que contiene OpenApi
        assertTrue(classes.contains(OpenApiResource.class));
    }
    
    @Test
    void testGetClassesSize() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();
        
        // Verificar el tamaño total esperado (12 recursos + 4 filtros/config + 1 OpenApi = 17)
        assertEquals(17, classes.size());
    }
    
    @Test
    void testGetClassesNoDuplicates() {
        MiApp app = new MiApp();
        Set<Class<?>> classes = app.getClasses();
        
        // Verificar que no hay duplicados (Set no permite duplicados)
        assertEquals(classes.size(), classes.stream().distinct().count());
    }
} 