package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.IntervencionDto;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.IntervencionMapper;
import codigocreativo.uy.servidorapp.entidades.Intervencion;
import codigocreativo.uy.servidorapp.entidades.TiposIntervencione;
import codigocreativo.uy.servidorapp.excepciones.ServiciosException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IntervencionBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private IntervencionMapper intervencionMapper;

    @InjectMocks
    private IntervencionBean intervencionBean;

    private IntervencionDto intervencionDto;
    private Intervencion intervencion;

    @BeforeEach
    void setUp() {
        intervencionDto = new IntervencionDto();
        intervencionDto.setId(1L);

        intervencion = new Intervencion();
        intervencion.setId(1L);
    }

    @Test
    void testCrear() throws ServiciosException {
        when(intervencionMapper.toEntity(any(IntervencionDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(intervencion);

        intervencionBean.crear(intervencionDto);

        verify(em, times(1)).persist(intervencion);
    }

    @Test
    void testActualizar() throws ServiciosException {
        when(intervencionMapper.toEntity(any(IntervencionDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(intervencion);

        intervencionBean.actualizar(intervencionDto);

        verify(em, times(1)).merge(intervencion);
    }

    @Test
    void testObtenerTodas() throws ServiciosException {
        TypedQuery<Intervencion> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT i FROM Intervencion i", Intervencion.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(intervencion));
        when(intervencionMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(intervencionDto));

        List<IntervencionDto> result = intervencionBean.obtenerTodas();

        assertEquals(1, result.size());
        assertEquals(intervencionDto, result.get(0));
    }

    @Test
    void testBuscarId() throws ServiciosException {
        when(em.find(Intervencion.class, 1L)).thenReturn(intervencion);
        when(intervencionMapper.toDto(any(Intervencion.class), any(CycleAvoidingMappingContext.class))).thenReturn(intervencionDto);

        IntervencionDto result = intervencionBean.buscarId(1L);

        assertEquals(intervencionDto, result);
    }

    @Test
    void testObtenerPorRangoDeFecha() throws ServiciosException {
        TypedQuery<Intervencion> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Intervencion.class))).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(intervencion));
        when(intervencionMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(intervencionDto));

        List<IntervencionDto> result = intervencionBean.obtenerPorRangoDeFecha(LocalDateTime.now(), LocalDateTime.now(), 1L);

        assertEquals(1, result.size());
        assertEquals(intervencionDto, result.get(0));
    }

    @Test
    void testObtenerCantidadPorTipo() throws ServiciosException {
        // Create a mock Intervencion object with a non-null idTipo
        TiposIntervencione tipo = new TiposIntervencione();
        tipo.setNombreTipo("Tipo1");
        intervencion.setIdTipo(tipo);

        // Mock the query and its result
        TypedQuery<Intervencion> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Intervencion.class))).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(intervencion));

        // Call the method under test
        Map<String, Long> result = intervencionBean.obtenerCantidadPorTipo(LocalDateTime.now(), LocalDateTime.now(), 1L);

        // Assert the result
        assertEquals(1, result.size());
        assertEquals(1L, result.get("Tipo1"));
    }
}