package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BajaEquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private BajaEquipoMapper bajaEquipoMapper;

    @InjectMocks
    private BajaEquipoBean bajaEquipoBean;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        bajaEquipoBean = new BajaEquipoBean(bajaEquipoMapper);

        // Utiliza reflexión para asignar el EntityManager privado
        Field emField = BajaEquipoBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(bajaEquipoBean, em);
    }

    @Test
    void testCrearBajaEquipo_success() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        BajaEquipo bajaEquipoEntity = new BajaEquipo();
        when(bajaEquipoMapper.toEntity(any(BajaEquipoDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(bajaEquipoEntity);

        assertDoesNotThrow(() -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto));
        verify(em, times(1)).persist(bajaEquipoEntity);
    }

    @Test
    void testCrearBajaEquipo_withMerge() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        BajaEquipo bajaEquipoEntity = new BajaEquipo();
        bajaEquipoEntity.setId(1L); // Simulamos que el ID no es nulo para probar el caso de merge
        when(bajaEquipoMapper.toEntity(any(BajaEquipoDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(bajaEquipoEntity);
        when(em.merge(bajaEquipoEntity)).thenReturn(bajaEquipoEntity);

        assertDoesNotThrow(() -> bajaEquipoBean.crearBajaEquipo(bajaEquipoDto));
        verify(em, times(1)).merge(bajaEquipoEntity);
        verify(em, times(1)).persist(bajaEquipoEntity);
    }

    @Test
    void testObtenerBajasEquipos_success() {
        List<BajaEquipo> bajaEquipoList = new ArrayList<>();
        TypedQuery<BajaEquipo> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(BajaEquipo.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(bajaEquipoList);
        when(bajaEquipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> bajaEquipoBean.obtenerBajasEquipos());
        verify(query, times(1)).getResultList();
    }

    @Test
    void testObtenerBajasEquipos_exception() {
        when(em.createQuery(anyString(), eq(BajaEquipo.class))).thenThrow(new RuntimeException("Simulated exception"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> bajaEquipoBean.obtenerBajasEquipos());
        assertNotNull(thrown);
        assertEquals("Simulated exception", thrown.getMessage());
    }

    @Test
    void testObtenerBajaEquipo_success() {
        Long id = 1L;
        BajaEquipo bajaEquipoEntity = new BajaEquipo();
        when(em.find(BajaEquipo.class, id)).thenReturn(bajaEquipoEntity);
        when(bajaEquipoMapper.toDto(any(BajaEquipo.class), any(CycleAvoidingMappingContext.class))).thenReturn(new BajaEquipoDto());

        assertDoesNotThrow(() -> bajaEquipoBean.obtenerBajaEquipo(id));
        verify(em, times(1)).find(BajaEquipo.class, id);
    }

    @Test
    void testObtenerBajaEquipo_notFound() {
        Long id = 1L;
        when(em.find(BajaEquipo.class, id)).thenReturn(null);

        BajaEquipoDto result = bajaEquipoBean.obtenerBajaEquipo(id);
        assertNull(result);
        verify(em, times(1)).find(BajaEquipo.class, id);
    }
}
