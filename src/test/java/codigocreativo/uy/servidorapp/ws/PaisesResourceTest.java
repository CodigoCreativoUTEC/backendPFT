package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.PaisDto;
import codigocreativo.uy.servidorapp.servicios.PaisRemote;
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

class PaisesResourceTest {

    @InjectMocks
    private PaisesResource paisesResource;

    @Mock
    private PaisRemote er;

    private PaisDto paisDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        paisDto = new PaisDto(); // inicializar con valores si es necesario
    }

    @Test
    void testCrearPais() {
        doNothing().when(er).crearPais(any(PaisDto.class));

        Response response = paisesResource.crearPais(paisDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(er, times(1)).crearPais(any(PaisDto.class));
    }

    @Test
    void testModificarPais() {
        doNothing().when(er).modificarPais(any(PaisDto.class));

        Response response = paisesResource.modificarPais(paisDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(er, times(1)).modificarPais(any(PaisDto.class));
    }

    @Test
    void testListarPaises() {
        List<PaisDto> expectedList = List.of(paisDto);
        when(er.obtenerpais()).thenReturn(expectedList);

        List<PaisDto> result = paisesResource.listarPaises();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(er, times(1)).obtenerpais();
    }
}
