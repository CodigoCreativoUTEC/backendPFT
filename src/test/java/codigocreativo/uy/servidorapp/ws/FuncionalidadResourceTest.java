package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.FuncionalidadDto;
import codigocreativo.uy.servidorapp.servicios.FuncionalidadRemote;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FuncionalidadResourceTest {

    @InjectMocks
    private FuncionalidadResource funcionalidadResource;

    @Mock
    private FuncionalidadRemote funcionalidadRemote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearFuncionalidad() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();

        // No es necesario hacer doNothing() para un método void
        // Simplemente llamamos a when con el método en cuestión
        when(funcionalidadRemote.crear(any(FuncionalidadDto.class))).thenReturn(null);

        Response response = funcionalidadResource.crear(funcionalidadDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(funcionalidadRemote, times(1)).crear(any(FuncionalidadDto.class));
    }

    @Test
    void testModificarFuncionalidad() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();

        when(funcionalidadRemote.actualizar(any(FuncionalidadDto.class))).thenReturn(null);

        Response response = funcionalidadResource.modificar(funcionalidadDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(funcionalidadRemote, times(1)).actualizar(any(FuncionalidadDto.class));
    }

    @Test
    void testEliminarFuncionalidad() {
        doNothing().when(funcionalidadRemote).eliminar(anyLong());

        Response response = funcionalidadResource.eliminar(1L);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(funcionalidadRemote, times(1)).eliminar(anyLong());
    }

    @Test
    void testListarFuncionalidades() {
        List<FuncionalidadDto> funcionalidades = List.of(new FuncionalidadDto(), new FuncionalidadDto());
        when(funcionalidadRemote.obtenerTodas()).thenReturn(funcionalidades);

        List<FuncionalidadDto> result = funcionalidadResource.listar();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(funcionalidadRemote, times(1)).obtenerTodas();
    }

    @Test
    void testBuscarFuncionalidadPorId() {
        FuncionalidadDto funcionalidadDto = new FuncionalidadDto();
        funcionalidadDto.setId(1L);
        when(funcionalidadRemote.buscarPorId(1L)).thenReturn(funcionalidadDto);

        FuncionalidadDto result = funcionalidadResource.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(funcionalidadRemote, times(1)).buscarPorId(anyLong());
    }
}
