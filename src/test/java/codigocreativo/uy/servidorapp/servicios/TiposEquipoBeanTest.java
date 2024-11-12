package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtomappers.TiposEquipoMapper;
import codigocreativo.uy.servidorapp.dtos.TiposEquipoDto;
import codigocreativo.uy.servidorapp.entidades.TiposEquipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    class TiposEquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private TiposEquipoMapper tiposEquipoMapper;

    @InjectMocks
    private TiposEquipoBean tiposEquipoBean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCrearTiposEquipo() {
        TiposEquipoDto tiposEquipoDto = new TiposEquipoDto();
        TiposEquipo tiposEquipoEntity = new TiposEquipo();

        when(tiposEquipoMapper.toEntity(tiposEquipoDto)).thenReturn(tiposEquipoEntity);

        tiposEquipoBean.crearTiposEquipo(tiposEquipoDto);

        verify(tiposEquipoMapper, times(1)).toEntity(tiposEquipoDto);
        verify(em, times(1)).persist(tiposEquipoEntity);
        verify(em, times(1)).flush();
    }

    @Test
     void testModificarTiposEquipo() {
        TiposEquipoDto tiposEquipoDto = new TiposEquipoDto();
        TiposEquipo tiposEquipoEntity = new TiposEquipo();

        when(tiposEquipoMapper.toEntity(tiposEquipoDto)).thenReturn(tiposEquipoEntity);

        tiposEquipoBean.modificarTiposEquipo(tiposEquipoDto);

        verify(tiposEquipoMapper, times(1)).toEntity(tiposEquipoDto);
        verify(em, times(1)).merge(tiposEquipoEntity);
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarTiposEquipo() {
        Long id = 1L;
        TiposEquipo tiposEquipoEntity = new TiposEquipo();

        when(em.find(TiposEquipo.class, id)).thenReturn(tiposEquipoEntity);

        tiposEquipoBean.eliminarTiposEquipo(id);

        verify(em, times(1)).find(TiposEquipo.class, id);
        assertEquals("INACTIVO", tiposEquipoEntity.getEstado());
        verify(em, times(1)).merge(tiposEquipoEntity);
    }

    @Test
     void testObtenerPorId() {
        Long id = 1L;
        TiposEquipo tiposEquipoEntity = new TiposEquipo();
        TiposEquipoDto tiposEquipoDto = new TiposEquipoDto();

        when(em.find(TiposEquipo.class, id)).thenReturn(tiposEquipoEntity);
        when(tiposEquipoMapper.toDto(tiposEquipoEntity)).thenReturn(tiposEquipoDto);

        TiposEquipoDto result = tiposEquipoBean.obtenerPorId(id);

        verify(em, times(1)).find(TiposEquipo.class, id);
        verify(tiposEquipoMapper, times(1)).toDto(tiposEquipoEntity);
        assertEquals(tiposEquipoDto, result);
    }

    @Test
     void testListarTiposEquipo() {
        List<TiposEquipo> tiposEquipos = new ArrayList<>();
        List<TiposEquipoDto> tiposEquipoDtos = new ArrayList<>();
        TypedQuery<TiposEquipo> query = mock(TypedQuery.class);

        when(em.createQuery("SELECT t FROM TiposEquipo t", TiposEquipo.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(tiposEquipos);
        when(tiposEquipoMapper.toDto(tiposEquipos)).thenReturn(tiposEquipoDtos);

        List<TiposEquipoDto> result = tiposEquipoBean.listarTiposEquipo();

        verify(em, times(1)).createQuery("SELECT t FROM TiposEquipo t", TiposEquipo.class);
        verify(query, times(1)).getResultList();
        verify(tiposEquipoMapper, times(1)).toDto(tiposEquipos);
        assertEquals(tiposEquipoDtos, result);
    }
}
