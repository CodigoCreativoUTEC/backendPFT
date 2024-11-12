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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
