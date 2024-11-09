package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.entidades.Equipo;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private EquipoMapper equipoMapper;

    @Mock
    private BajaEquipoMapper bajaEquipoMapper;

    @InjectMocks
    private EquipoBean equipoBean;

    private EquipoDto equipoDto;
    private Equipo equipo;
    private BajaEquipoDto bajaEquipoDto;

    @BeforeEach
    void setUp() {
        equipoDto = new EquipoDto();
        equipoDto.setId(1L);
        equipoDto.setNombre("Equipo Test");

        equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Equipo Test");

        bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setId(1L);
        bajaEquipoDto.setIdEquipo(equipoDto);
    }

    @Test
    void testCrearEquipo() {
        when(equipoMapper.toEntity(any(EquipoDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(equipo);

        equipoBean.crearEquipo(equipoDto);

        verify(em, times(1)).persist(equipo);
        verify(em, times(1)).flush();
    }

    @Test
    void testModificarEquipo() {
        when(equipoMapper.toEntity(any(EquipoDto.class), any(CycleAvoidingMappingContext.class))).thenReturn(equipo);

        equipoBean.modificarEquipo(equipoDto);

        verify(em, times(1)).merge(equipo);
        verify(em, times(1)).flush();
    }

    @Test
    void testEliminarEquipo() {
        Query mockedQuery = mock(Query.class);
        when(em.createQuery("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id")).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", equipoDto.getId())).thenReturn(mockedQuery);

        equipoBean.eliminarEquipo(bajaEquipoDto);

        verify(em, times(1)).merge(any());
        verify(em, times(1)).createQuery("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id");
        verify(mockedQuery, times(1)).setParameter("id", equipoDto.getId());
        verify(mockedQuery, times(1)).executeUpdate();
        verify(em, times(1)).flush();
    }

    @Test
    void testObtenerEquipo() {
        when(em.find(Equipo.class, 1L)).thenReturn(equipo);
        when(equipoMapper.toDto(any(Equipo.class), any(CycleAvoidingMappingContext.class))).thenReturn(equipoDto);

        EquipoDto result = equipoBean.obtenerEquipo(1L);

        assertEquals(equipoDto, result);
    }

    @Test
    void testListarEquipos() {
        TypedQuery<Equipo> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT equipo FROM Equipo equipo", Equipo.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(equipo));
        when(equipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(equipoDto));

        List<EquipoDto> result = equipoBean.listarEquipos();

        assertEquals(1, result.size());
        assertEquals(equipoDto, result.get(0));
    }

    @Test
    void testObtenerEquiposFiltrado() {
        TypedQuery<Equipo> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Equipo.class))).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.singletonList(equipo));
        when(equipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(equipoDto));

        Map<String, String> filtros = Map.of("nombre", "Equipo Test");
        List<EquipoDto> result = equipoBean.obtenerEquiposFiltrado(filtros);

        assertEquals(1, result.size());
        assertEquals(equipoDto, result.get(0));
    }
}