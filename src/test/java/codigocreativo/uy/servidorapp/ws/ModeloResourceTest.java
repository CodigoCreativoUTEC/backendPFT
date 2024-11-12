package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.ModelosEquipoDto;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.servicios.ModelosEquipoRemote;
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

class ModeloResourceTest {

    @InjectMocks
    private ModeloResource modeloResource;

    @Mock
    private ModelosEquipoRemote er;

    private ModelosEquipoDto modeloDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        modeloDto = new ModelosEquipoDto(); // inicializar con valores si es necesario
    }

    @Test
    void testCrearModelo() {
        doNothing().when(er).crearModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.crearModelo(modeloDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(Estados.ACTIVO, modeloDto.getEstado());
        verify(er, times(1)).crearModelos(any(ModelosEquipoDto.class));
    }

    @Test
    void testModificarModelo() {
        doNothing().when(er).modificarModelos(any(ModelosEquipoDto.class));

        Response response = modeloResource.modificarModelo(modeloDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(er, times(1)).modificarModelos(any(ModelosEquipoDto.class));
    }

    @Test
    void testEliminarModelo() {
        Long id = 1L;
        doNothing().when(er).eliminarModelos(id);

        Response response = modeloResource.eliminarModelo(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(er, times(1)).eliminarModelos(id);
    }

    @Test
    void testListarTodos() {
        List<ModelosEquipoDto> expectedList = List.of(modeloDto);
        when(er.listarModelos()).thenReturn(expectedList);

        List<ModelosEquipoDto> result = modeloResource.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(er, times(1)).listarModelos();
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        when(er.obtenerModelos(id)).thenReturn(modeloDto);

        ModelosEquipoDto result = modeloResource.buscarPorId(id);

        assertNotNull(result);
        assertEquals(modeloDto, result);
        verify(er, times(1)).obtenerModelos(id);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        Long id = 1L;
        when(er.obtenerModelos(id)).thenReturn(null);

        ModelosEquipoDto result = modeloResource.buscarPorId(id);

        assertEquals(null, result);
        verify(er, times(1)).obtenerModelos(id);
    }
}
