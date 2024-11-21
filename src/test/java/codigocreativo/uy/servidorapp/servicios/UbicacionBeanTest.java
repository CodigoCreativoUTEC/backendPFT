package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.enumerados.Estados;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UbicacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private UbicacionMapper ubicacionMapper;

    @Mock
    private EquipoMapper equipoMapper;

    @InjectMocks
    private UbicacionBean ubicacionBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ubicacionBean = new UbicacionBean(ubicacionMapper, equipoMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = UbicacionBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(ubicacionBean, em);
    }

    @Test
    void testCrearUbicacion() throws ServiciosException {
        UbicacionDto ubicacionDto = new UbicacionDto();
        Ubicacion ubicacionEntity = new Ubicacion();

        when(ubicacionMapper.toEntity(ubicacionDto)).thenReturn(ubicacionEntity);

        ubicacionBean.crearUbicacion(ubicacionDto);

        verify(em).persist(ubicacionEntity);
        verify(em).flush();
    }

    @Test
    void testModificarUbicacion() throws ServiciosException {
        UbicacionDto ubicacionDto = new UbicacionDto();
        Ubicacion ubicacionEntity = new Ubicacion();

        when(ubicacionMapper.toEntity(ubicacionDto)).thenReturn(ubicacionEntity);

        ubicacionBean.modificarUbicacion(ubicacionDto);

        verify(em).merge(ubicacionEntity);
        verify(em).flush();
    }

    @Test
    void testBorrarUbicacion() throws ServiciosException {
        Long id = 1L;

        Query query = mock(Query.class);
        when(em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = 'INACTIVO' WHERE ubicacion.id = :id")).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);

        ubicacionBean.borrarUbicacion(id);

        verify(query).setParameter("id", id);
        verify(query).executeUpdate();
    }

    @Test
    void testMoverEquipoDeUbicacion() throws ServiciosException {
        EquipoDto equipoDto = new EquipoDto();
        UbicacionDto ubicacionDto = new UbicacionDto();

        ubicacionBean.moverEquipoDeUbicacion(equipoDto, ubicacionDto);

        verify(equipoMapper).toEntity(eq(equipoDto), any());
        verify(em).merge(any());
        verify(em).flush();
    }

    @Test
    void testListarUbicaciones() throws ServiciosException {
        Ubicacion ubicacionEntity = new Ubicacion();
        List<Ubicacion> ubicaciones = Collections.singletonList(ubicacionEntity);

        TypedQuery<Ubicacion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.estado = 'ACTIVO'", Ubicacion.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(ubicaciones);
        when(ubicacionMapper.toDto(ubicaciones)).thenReturn(Collections.singletonList(new UbicacionDto()));

        List<UbicacionDto> result = ubicacionBean.listarUbicaciones();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testObtenerUbicacionPorId() throws ServiciosException {
        Long id = 1L;
        Ubicacion ubicacionEntity = new Ubicacion();
        UbicacionDto ubicacionDto = new UbicacionDto();

        TypedQuery<Ubicacion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class)).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(ubicacionEntity);
        when(ubicacionMapper.toDto(ubicacionEntity)).thenReturn(ubicacionDto);

        UbicacionDto result = ubicacionBean.obtenerUbicacionPorId(id);

        assertNotNull(result);
    }

    @Test
    void testBajaLogicaUbicacion() throws ServiciosException {
        UbicacionDto ubicacionDto = new UbicacionDto();
        Ubicacion ubicacionEntity = new Ubicacion();
        ubicacionDto.setEstado(Estados.ACTIVO);

        when(ubicacionMapper.toEntity(ubicacionDto)).thenReturn(ubicacionEntity);

        ubicacionBean.bajaLogicaUbicacion(ubicacionDto);

        assertEquals(Estados.INACTIVO, ubicacionEntity.getEstado());
        verify(em).merge(ubicacionEntity);
        verify(em).flush();
    }
}
