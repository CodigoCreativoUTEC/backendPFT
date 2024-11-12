package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ProveedoresEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ProveedoresEquipoRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProveedoresResourceTest {

    @Mock
    private ProveedoresEquipoRemote er;

    @InjectMocks
    private ProveedoresResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearProveedor() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto();
        Response response = resource.crearProveedor(dto);

        verify(er, times(1)).crearProveedor(dto);
        assertEquals(201, response.getStatus());
    }

    @Test
    void testModificarProveedor() {
        ProveedoresEquipoDto dto = new ProveedoresEquipoDto();
        Response response = resource.modificarProveedor(dto);

        verify(er, times(1)).modificarProveedor(dto);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEliminarProveedor() {
        Long id = 1L;
        Response response = resource.eliminarProveedor(id);

        verify(er, times(1)).eliminarProveedor(id);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testListarProveedores() {
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(new ProveedoresEquipoDto(), new ProveedoresEquipoDto());
        when(er.obtenerProveedores()).thenReturn(expectedList);

        List<ProveedoresEquipoDto> actualList = resource.listarProveedores();

        verify(er, times(1)).obtenerProveedores();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        ProveedoresEquipoDto expectedDto = new ProveedoresEquipoDto();
        when(er.obtenerProveedor(id)).thenReturn(expectedDto);

        ProveedoresEquipoDto actualDto = resource.buscarPorId(id);

        verify(er, times(1)).obtenerProveedor(id);
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testBuscarProveedores() {
        String nombre = "ProveedorX";
        Estados estado = Estados.ACTIVO;
        List<ProveedoresEquipoDto> expectedList = Arrays.asList(new ProveedoresEquipoDto(), new ProveedoresEquipoDto());
        when(er.buscarProveedores(nombre, estado)).thenReturn(expectedList);

        List<ProveedoresEquipoDto> actualList = resource.buscarProveedores(nombre, estado);

        verify(er, times(1)).buscarProveedores(nombre, estado);
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
    }
}
