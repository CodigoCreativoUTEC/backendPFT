package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.BajaUbicacionMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.dtos.BajaUbicacionDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.entidades.BajaUbicacion;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BajaUbicacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private BajaUbicacionMapper bajaUbicacionMapper;

    @Mock
    private UbicacionMapper ubicacionMapper;

    @Mock
    private BajaUbicacionRemote bajaUbicacionRemote;

    @InjectMocks
    private BajaUbicacionBean bajaUbicacionBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bajaUbicacionBean = new BajaUbicacionBean(bajaUbicacionMapper, ubicacionMapper);
        bajaUbicacionBean.em = em;
    }

    @Test
    void testCrearBajaUbicacion_success() {
        BajaUbicacionDto dto = new BajaUbicacionDto();
        BajaUbicacion bajaUbicacion = new BajaUbicacion();
        when(bajaUbicacionMapper.toEntity(any(BajaUbicacionDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(bajaUbicacion);

        assertDoesNotThrow(() -> bajaUbicacionBean.crearBajaUbicacion(dto));
        verify(em, times(1)).persist(bajaUbicacion);
    }

    @Test
    void testCrearBajaUbicacion_exception() {
        BajaUbicacionDto dto = new BajaUbicacionDto();
        when(bajaUbicacionMapper.toEntity(any(BajaUbicacionDto.class), any(CycleAvoidingMappingContext.class))).thenThrow(new RuntimeException("Simulated exception"));

        ServiciosException thrown = assertThrows(ServiciosException.class, () -> bajaUbicacionBean.crearBajaUbicacion(dto));
        assertNotNull(thrown);
        assertEquals("Simulated exception", thrown.getMessage());
    }

    @Test
    void testBorrarUbicacion_success() {
        Long id = 1L;
        Ubicacion ubicacion = new Ubicacion();
        when(em.find(Ubicacion.class, id)).thenReturn(ubicacion);

        assertDoesNotThrow(() -> bajaUbicacionBean.borrarUbicacion(id));
        verify(em, times(1)).remove(ubicacion);
        verify(em, times(1)).flush();
    }

    @Test
    void testBorrarUbicacion_notFound() {
        Long id = 1L;
        when(em.find(Ubicacion.class, id)).thenReturn(null);

        ServiciosException thrown = assertThrows(ServiciosException.class, () -> bajaUbicacionBean.borrarUbicacion(id));
        assertNotNull(thrown);
        assertEquals("No se pudo borrar la ubicacion", thrown.getMessage());
    }

    @Test
    void testListarBajaUbicaciones_success() {
        List<BajaUbicacion> bajaUbicacionList = new ArrayList<>();
        TypedQuery<BajaUbicacion> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(BajaUbicacion.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(bajaUbicacionList);
        when(bajaUbicacionMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> bajaUbicacionBean.listarBajaUbicaciones());
        verify(query, times(1)).getResultList();
    }

    @Test
    void testListarBajaUbicaciones_exception() {
        when(em.createQuery(anyString(), eq(BajaUbicacion.class))).thenThrow(new RuntimeException("Simulated exception"));

        ServiciosException thrown = assertThrows(ServiciosException.class, () -> bajaUbicacionBean.listarBajaUbicaciones());
        assertNotNull(thrown);
        assertEquals("No se pudo listar las bajas de ubicaciones", thrown.getMessage());
    }

    @Test
    void testBajaLogicaUbicacion_success() {
        UbicacionDto ubicacionDto = new UbicacionDto();
        Ubicacion ubicacion = new Ubicacion();
        when(ubicacionMapper.toEntity(ubicacionDto)).thenReturn(ubicacion);

        assertDoesNotThrow(() -> bajaUbicacionBean.bajaLogicaUbicacion(ubicacionDto));
        assertEquals(Estados.INACTIVO, ubicacion.getEstado());
        verify(em, times(1)).merge(ubicacion);
        verify(em, times(1)).flush();
    }

    @Test
    void testBajaLogicaUbicacion_exception() {
        UbicacionDto ubicacionDto = new UbicacionDto();
        when(ubicacionMapper.toEntity(ubicacionDto)).thenThrow(new RuntimeException("Simulated exception"));

        ServiciosException thrown = assertThrows(ServiciosException.class, () -> {
            bajaUbicacionBean.bajaLogicaUbicacion(ubicacionDto);
        });
        assertNotNull(thrown);
        assertEquals("Simulated exception", thrown.getMessage());
    }
}
