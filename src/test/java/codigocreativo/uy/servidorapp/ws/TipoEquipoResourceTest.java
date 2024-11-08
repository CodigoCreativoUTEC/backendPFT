package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.servicios.TiposEquipoRemote;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TipoEquipoResourceTest {

    @Mock
    private TiposEquipoRemote er;

    @InjectMocks
    private TipoEquipoResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrear() {
        TiposEquipoDto dto = new TiposEquipoDto();
        Response response = resource.crear(dto);
        verify(er, times(1)).crearTiposEquipo(dto);
        assertEquals(201, response.getStatus());
    }

    @Test
    void testModificar() {
        TiposEquipoDto dto = new TiposEquipoDto();
        Response response = resource.modificar(dto);
        verify(er, times(1)).modificarTiposEquipo(dto);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEliminar() {
        Long id = 1L;
        Response response = resource.eliminar(id);
        verify(er, times(1)).eliminarTiposEquipo(id);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testListar() {
        List<TiposEquipoDto> expectedList = Arrays.asList(new TiposEquipoDto(), new TiposEquipoDto());
        when(er.listarTiposEquipo()).thenReturn(expectedList);
        List<TiposEquipoDto> actualList = resource.listar();
        verify(er, times(1)).listarTiposEquipo();
        assertEquals(expectedList, actualList);
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        TiposEquipoDto expectedDto = new TiposEquipoDto();
        when(er.obtenerPorId(id)).thenReturn(expectedDto);
        TiposEquipoDto actualDto = resource.buscarPorId(id);
        verify(er, times(1)).obtenerPorId(id);
        assertEquals(expectedDto, actualDto);
    }
}