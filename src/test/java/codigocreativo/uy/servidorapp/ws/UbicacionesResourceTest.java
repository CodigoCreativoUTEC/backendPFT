package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import codigocreativo.uy.servidorapp.servicios.UbicacionRemote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UbicacionesResourceTest {

    @Mock
    private UbicacionRemote er;

    @InjectMocks
    private UbicacionesResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarUbicacionesSuccess() throws ServiciosException {
        List<UbicacionDto> expectedList = Arrays.asList(new UbicacionDto(), new UbicacionDto());
        when(er.listarUbicaciones()).thenReturn(expectedList);

        List<UbicacionDto> actualList = resource.listarUbicaciones();

        verify(er, times(1)).listarUbicaciones();
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList, actualList);
    }

    @Test
    void testListarUbicacionesThrowsException() throws ServiciosException {
        when(er.listarUbicaciones()).thenThrow(new ServiciosException("Database error"));

        ServiciosException exception = assertThrows(ServiciosException.class, () -> {
            resource.listarUbicaciones();
        });

        verify(er, times(1)).listarUbicaciones();
        assertEquals("Error al listar ubicaciones", exception.getMessage());
    }

    @Test
    void testListarUbicacionesWithEmptyList() throws ServiciosException {
        List<UbicacionDto> emptyList = Arrays.asList();
        when(er.listarUbicaciones()).thenReturn(emptyList);

        List<UbicacionDto> actualList = resource.listarUbicaciones();

        verify(er, times(1)).listarUbicaciones();
        assertEquals(0, actualList.size());
        assertTrue(actualList.isEmpty());
    }

    @Test
    void testListarUbicacionesWithNullList() throws ServiciosException {
        when(er.listarUbicaciones()).thenReturn(null);

        List<UbicacionDto> actualList = resource.listarUbicaciones();

        verify(er, times(1)).listarUbicaciones();
        assertNull(actualList);
    }

    @Test
    void testBuscarPorIdSuccess() throws ServiciosException {
        UbicacionDto expectedUbicacion = new UbicacionDto();
        expectedUbicacion.setId(1L);
        expectedUbicacion.setNombre("Ubicación Test");
        
        when(er.obtenerUbicacionPorId(1L)).thenReturn(expectedUbicacion);

        UbicacionDto actualUbicacion = resource.buscarPorId(1L);

        verify(er, times(1)).obtenerUbicacionPorId(1L);
        assertNotNull(actualUbicacion);
        assertEquals(expectedUbicacion.getId(), actualUbicacion.getId());
        assertEquals(expectedUbicacion.getNombre(), actualUbicacion.getNombre());
    }

    @Test
    void testBuscarPorIdWithNullId() throws ServiciosException {
        when(er.obtenerUbicacionPorId(null)).thenReturn(null);

        UbicacionDto actualUbicacion = resource.buscarPorId(null);

        verify(er, times(1)).obtenerUbicacionPorId(null);
        assertNull(actualUbicacion);
    }

    @Test
    void testBuscarPorIdWithNonExistentId() throws ServiciosException {
        when(er.obtenerUbicacionPorId(999L)).thenReturn(null);

        UbicacionDto actualUbicacion = resource.buscarPorId(999L);

        verify(er, times(1)).obtenerUbicacionPorId(999L);
        assertNull(actualUbicacion);
    }

    @Test
    void testBuscarPorIdThrowsException() throws ServiciosException {
        when(er.obtenerUbicacionPorId(anyLong())).thenThrow(new ServiciosException("Database error"));

        ServiciosException exception = assertThrows(ServiciosException.class, () -> {
            resource.buscarPorId(1L);
        });

        verify(er, times(1)).obtenerUbicacionPorId(1L);
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testBuscarPorIdWithZeroId() throws ServiciosException {
        when(er.obtenerUbicacionPorId(0L)).thenReturn(null);

        UbicacionDto actualUbicacion = resource.buscarPorId(0L);

        verify(er, times(1)).obtenerUbicacionPorId(0L);
        assertNull(actualUbicacion);
    }

    @Test
    void testBuscarPorIdWithNegativeId() throws ServiciosException {
        when(er.obtenerUbicacionPorId(-1L)).thenReturn(null);

        UbicacionDto actualUbicacion = resource.buscarPorId(-1L);

        verify(er, times(1)).obtenerUbicacionPorId(-1L);
        assertNull(actualUbicacion);
    }

    @Test
    void testBuscarPorIdWithLargeId() throws ServiciosException {
        UbicacionDto expectedUbicacion = new UbicacionDto();
        expectedUbicacion.setId(Long.MAX_VALUE);
        expectedUbicacion.setNombre("Ubicación con ID máximo");
        
        when(er.obtenerUbicacionPorId(Long.MAX_VALUE)).thenReturn(expectedUbicacion);

        UbicacionDto actualUbicacion = resource.buscarPorId(Long.MAX_VALUE);

        verify(er, times(1)).obtenerUbicacionPorId(Long.MAX_VALUE);
        assertNotNull(actualUbicacion);
        assertEquals(Long.MAX_VALUE, actualUbicacion.getId());
    }
}
