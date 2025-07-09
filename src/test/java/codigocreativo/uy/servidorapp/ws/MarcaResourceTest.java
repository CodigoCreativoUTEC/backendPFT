package codigocreativo.uy.servidorapp.ws;

import codigocreativo.uy.servidorapp.dtos.MarcasModeloDto;
import codigocreativo.uy.servidorapp.servicios.MarcasModeloRemote;
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

class MarcaResourceTest {

    @InjectMocks
    private MarcaResource marcaResource;

    @Mock
    private MarcasModeloRemote er;

    private MarcasModeloDto marcaDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        marcaDto = new MarcasModeloDto(); // inicializar con valores si es necesario
    }

    @Test
    void testCrearMarca() {
        doNothing().when(er).crearMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.crearMarca(marcaDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(er, times(1)).crearMarcasModelo(any(MarcasModeloDto.class));
    }

    @Test
    void testEliminarMarca() {
        Long id = 1L;
        doNothing().when(er).eliminarMarca(id);

        Response response = marcaResource.eliminarMarca(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(er, times(1)).eliminarMarca(id);
    }

    @Test
    void testListarTodas() {
        List<MarcasModeloDto> expectedList = List.of(marcaDto);
        when(er.obtenerMarcasLista()).thenReturn(expectedList);

        List<MarcasModeloDto> result = marcaResource.listarTodas();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(er, times(1)).obtenerMarcasLista();
    }

    @Test
    void testBuscarPorId() {
        Long id = 1L;
        when(er.obtenerMarca(id)).thenReturn(marcaDto);

        MarcasModeloDto result = marcaResource.buscarPorId(id);

        assertNotNull(result);
        assertEquals(marcaDto, result);
        verify(er, times(1)).obtenerMarca(id);
    }

    @Test
    void testBuscarPorIdNoEncontrado() {
        Long id = 1L;
        when(er.obtenerMarca(id)).thenReturn(null);

        MarcasModeloDto result = marcaResource.buscarPorId(id);

        assertEquals(null, result);
        verify(er, times(1)).obtenerMarca(id);
    }

    @Test
    void testModificarMarca_estadoPermitido() {
        marcaDto.setId(1L);
        marcaDto.setNombre("Marca1");
        marcaDto.setEstado(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        MarcasModeloDto actual = new MarcasModeloDto();
        actual.setId(1L);
        actual.setNombre("Marca1");
        actual.setEstado(codigocreativo.uy.servidorapp.enumerados.Estados.INACTIVO);
        when(er.obtenerMarca(1L)).thenReturn(actual);
        doNothing().when(er).modificarMarcasModelo(any(MarcasModeloDto.class));

        Response response = marcaResource.modificarMarca(marcaDto);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testModificarMarca_nombreNoPermitido() {
        marcaDto.setId(1L);
        marcaDto.setNombre("MarcaNueva"); // nombre diferente
        marcaDto.setEstado(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        MarcasModeloDto actual = new MarcasModeloDto();
        actual.setId(1L);
        actual.setNombre("MarcaOriginal");
        actual.setEstado(codigocreativo.uy.servidorapp.enumerados.Estados.ACTIVO);
        when(er.obtenerMarca(1L)).thenReturn(actual);

        Response response = marcaResource.modificarMarca(marcaDto);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
