package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.UbicacionDto;
import codigocreativo.uy.servidorapp.dtomappers.UbicacionMapper;
import codigocreativo.uy.servidorapp.entidades.Ubicacion;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbicacionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private UbicacionMapper ubicacionMapper;

    @InjectMocks
    private UbicacionBean ubicacionBean;

    private UbicacionDto ubicacionDto;
    private Ubicacion ubicacion;

    @BeforeEach
    void setUp() {
        ubicacionDto = new UbicacionDto();
        ubicacionDto.setId(1L);
        ubicacionDto.setNombre("Ubicacion Test");

        ubicacion = new Ubicacion();
        ubicacion.setId(1L);
        ubicacion.setNombre("Ubicacion Test");
    }

    @Test
    void testCrearUbicacion() throws ServiciosException {
        when(ubicacionMapper.toEntity(any(UbicacionDto.class))).thenReturn(ubicacion);

        ubicacionBean.crearUbicacion(ubicacionDto);

        verify(em, times(1)).persist(ubicacion);
        verify(em, times(1)).flush();
    }

    @Test
    void testModificarUbicacion() throws ServiciosException {
        when(ubicacionMapper.toEntity(any(UbicacionDto.class))).thenReturn(ubicacion);

        ubicacionBean.modificarUbicacion(ubicacionDto);

        verify(em, times(1)).merge(ubicacion);
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarUbicacion() throws ServiciosException {
        Query mockedQuery = mock(Query.class);
        when(em.createQuery("UPDATE Ubicacion ubicacion SET ubicacion.estado = 'INACTIVO' WHERE ubicacion.id = :id")).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", 1L)).thenReturn(mockedQuery);

        ubicacionBean.borrarUbicacion(1L);

        verify(mockedQuery, times(1)).setParameter("id", 1L);
        verify(mockedQuery, times(1)).executeUpdate();
    }

    @Test
    void testObtenerUbicacion() throws ServiciosException {
        TypedQuery<Ubicacion> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.id = :id", Ubicacion.class)).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", 1L)).thenReturn(mockedQuery);
        when(mockedQuery.getSingleResult()).thenReturn(ubicacion);
        when(ubicacionMapper.toDto(any(Ubicacion.class))).thenReturn(ubicacionDto);

        UbicacionDto result = ubicacionBean.obtenerUbicacionPorId(1L);

        assertEquals(ubicacionDto, result);
    }

    @Test
    void testListarUbicaciones() throws ServiciosException {
        TypedQuery<Ubicacion> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT u FROM Ubicacion u WHERE u.estado = 'ACTIVO'", Ubicacion.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(ubicacion));
        when(ubicacionMapper.toDto(anyList())).thenReturn(Collections.singletonList(ubicacionDto));

        List<UbicacionDto> result = ubicacionBean.listarUbicaciones();

        assertEquals(1, result.size());
        assertEquals(ubicacionDto, result.get(0));
    }
}