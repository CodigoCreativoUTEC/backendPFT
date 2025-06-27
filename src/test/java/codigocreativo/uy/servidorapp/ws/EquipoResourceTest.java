package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.servicios.BajaEquipoRemote;
import codigocreativo.uy.servidorapp.servicios.EquipoRemote;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

class EquipoResourceTest {

    @InjectMocks
    private EquipoResource equipoResource;

    @Mock
    private EquipoRemote equipoRemote;

    @Mock
    private BajaEquipoRemote bajaEquipoRemote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEquipo() {
        EquipoDto equipo = new EquipoDto();

        doNothing().when(equipoRemote).crearEquipo(any(EquipoDto.class));

        Response response = equipoResource.crearEquipo(equipo);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(equipoRemote, times(1)).crearEquipo(any(EquipoDto.class));
    }

    @Test
    void testModificarEquipo() {
        EquipoDto equipo = new EquipoDto();

        doNothing().when(equipoRemote).modificarEquipo(any(EquipoDto.class));

        Response response = equipoResource.modificarProducto(equipo);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(equipoRemote, times(1)).modificarEquipo(any(EquipoDto.class));
    }

    @Test
    void testEliminarEquipo() {
        BajaEquipoDto equipo = new BajaEquipoDto();

        doNothing().when(equipoRemote).eliminarEquipo(any(BajaEquipoDto.class));

        Response response = equipoResource.eliminarEquipo(equipo);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(equipoRemote, times(1)).eliminarEquipo(any(BajaEquipoDto.class));
    }

    @Test
    void testObtenerTodosLosEquipos() {
        List<EquipoDto> equipos = List.of(new EquipoDto(), new EquipoDto());
        when(equipoRemote.listarEquipos()).thenReturn(equipos);

        List<EquipoDto> result = equipoResource.obtenerTodosLosEquipos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(equipoRemote, times(1)).listarEquipos();
    }

    @Test
    void testBuscarEquipo() {
        EquipoDto equipo = new EquipoDto();
        equipo.setId(1L);
        when(equipoRemote.obtenerEquipo(1L)).thenReturn(equipo);

        EquipoDto result = equipoResource.buscarEquipo(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFiltrarEquipos() {
        List<EquipoDto> equipos = List.of(new EquipoDto(), new EquipoDto());
        when(equipoRemote.obtenerEquiposFiltrado(any(Map.class))).thenReturn(equipos);

        List<EquipoDto> result = equipoResource.filtrar("nombre", "tipo", "marca", "modelo", "12345", "Uruguay", "ProveedorX", "2022-01-01", "001", "sala");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(equipoRemote, times(1)).obtenerEquiposFiltrado(any(Map.class));
    }

    @Test
    void testObtenerBajasEquipos() {
        List<BajaEquipoDto> bajas = List.of(new BajaEquipoDto(), new BajaEquipoDto());
        when(bajaEquipoRemote.obtenerBajasEquipos()).thenReturn(bajas);

        List<BajaEquipoDto> result = equipoResource.obtenerBajasEquipos();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testObtenerBaja() {
        BajaEquipoDto baja = new BajaEquipoDto();
        baja.setId(1L);
        when(bajaEquipoRemote.obtenerBajaEquipo(1L)).thenReturn(baja);

        BajaEquipoDto result = equipoResource.obtenerBaja(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
