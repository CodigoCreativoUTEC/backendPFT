package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.InstitucionDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.InstitucionMapper;
import codigocreativo.uy.servidorapp.entidades.Institucion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InstitucionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private InstitucionMapper institucionMapper;

    @InjectMocks
    private InstitucionBean institucionBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        institucionBean = new InstitucionBean(institucionMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = InstitucionBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(institucionBean, em);
    }

    @Test
    void testAgregar() {
        InstitucionDto institucionDto = new InstitucionDto();
        Institucion institucionEntity = new Institucion();

        when(institucionMapper.toEntity(institucionDto)).thenReturn(institucionEntity);

        institucionBean.agregar(institucionDto);

        verify(em).persist(institucionEntity);
        verify(em).flush();
    }

    @Test
    void testEliminarInstitucion() {
        InstitucionDto institucionDto = new InstitucionDto();
        Institucion institucionEntity = new Institucion();

        when(institucionMapper.toEntity(institucionDto)).thenReturn(institucionEntity);

        institucionBean.eliminarInstitucion(institucionDto);

        verify(em).remove(institucionEntity);
        verify(em).flush();
    }

    @Test
    void testModificar() {
        InstitucionDto institucionDto = new InstitucionDto();
        Institucion institucionEntity = new Institucion();

        when(institucionMapper.toEntity(institucionDto)).thenReturn(institucionEntity);

        institucionBean.modificar(institucionDto);

        verify(em).merge(institucionEntity);
        verify(em).flush();
    }

    @Test
    void testObtenerUbicaciones() {
        TypedQuery<Object> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT Ubicacion FROM Institucion i, Institucion.class")).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList("Ubicacion 1"));

        List<Object> result = institucionBean.obtenerUbicaciones();

        assertEquals(1, result.size());
        assertEquals("Ubicacion 1", result.get(0));
        verify(query).getResultList();
    }

    @Test
    void testObtenerInstituciones() {
        List<Institucion> instituciones = Collections.singletonList(new Institucion());
        List<InstitucionDto> institucionesDto = Collections.singletonList(new InstitucionDto());

        TypedQuery<Institucion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT i FROM Institucion i", Institucion.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(instituciones);
        when(institucionMapper.toDto(instituciones)).thenReturn(institucionesDto);

        List<InstitucionDto> result = institucionBean.obtenerInstituciones();

        assertEquals(institucionesDto, result);
        verify(query).getResultList();
    }

    @Test
    void testObtenerInstitucionPorNombre() {
        String nombre = "Institucion Test";
        Institucion institucion = new Institucion();
        InstitucionDto institucionDto = new InstitucionDto();

        TypedQuery<Institucion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT i FROM Institucion i WHERE i.nombre = :nombre", Institucion.class)).thenReturn(query);
        when(query.setParameter("nombre", nombre)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(institucion);
        when(institucionMapper.toDto(institucion)).thenReturn(institucionDto);

        InstitucionDto result = institucionBean.obtenerInstitucionPorNombre(nombre);

        assertEquals(institucionDto, result);
    }

    @Test
    void testObtenerInstitucionPorId() {
        Long id = 1L;
        Institucion institucion = new Institucion();
        InstitucionDto institucionDto = new InstitucionDto();

        TypedQuery<Institucion> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT i FROM Institucion i WHERE i.id = :id", Institucion.class)).thenReturn(query);
        when(query.setParameter("id", id)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(institucion);
        when(institucionMapper.toDto(institucion)).thenReturn(institucionDto);

        InstitucionDto result = institucionBean.obtenerInstitucionPorId(id);

        assertEquals(institucionDto, result);
    }
}
