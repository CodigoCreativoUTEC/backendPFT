package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PerfilDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.PerfilRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PerfilResourceTest {

    @InjectMocks
    private PerfilResource perfilResource;

    @Mock
    private PerfilRemote perfilRemote;

    private PerfilDto perfilDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        perfilDto = new PerfilDto(); // inicializar con valores si es necesario
    }

    @Test
    void testCrearPerfil() {
        doNothing().when(perfilRemote).crearPerfil(any(PerfilDto.class));

        Response response = perfilResource.crearPerfil(perfilDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).crearPerfil(any(PerfilDto.class));
    }

    @Test
    void testModificarPerfil() {
        doNothing().when(perfilRemote).modificarPerfil(any(PerfilDto.class));

        Response response = perfilResource.modificarPerfil(perfilDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).modificarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);
        doNothing().when(perfilRemote).eliminarPerfil(any(PerfilDto.class));

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, times(1)).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testEliminarPerfilNoExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(null);

        Response response = perfilResource.eliminarPerfil(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
        verify(perfilRemote, never()).eliminarPerfil(any(PerfilDto.class));
    }

    @Test
    void testObtenerPerfilExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(perfilDto);

        Response response = perfilResource.obtenerPerfil(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
    }

    @Test
    void testObtenerPerfilNoExistente() {
        Long id = 1L;
        when(perfilRemote.obtenerPerfil(id)).thenReturn(null);

        Response response = perfilResource.obtenerPerfil(id);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(perfilRemote, times(1)).obtenerPerfil(id);
    }

    @Test
    void testObtenerPerfiles() {
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.obtenerPerfiles()).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.obtenerPerfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).obtenerPerfiles();
    }

    @Test
    void testListarPerfilesPorNombre() {
        String nombre = "Admin";
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.listarPerfilesPorNombre(nombre)).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.listarPerfilesPorNombre(nombre);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).listarPerfilesPorNombre(nombre);
    }

    @Test
    void testListarPerfilesPorEstado() {
        Estados estado = Estados.ACTIVO;
        List<PerfilDto> expectedList = List.of(perfilDto);
        when(perfilRemote.listarPerfilesPorEstado(estado)).thenReturn(expectedList);

        List<PerfilDto> result = perfilResource.listarPerfilesPorEstado(estado);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(perfilRemote, times(1)).listarPerfilesPorEstado(estado);
    }
}
