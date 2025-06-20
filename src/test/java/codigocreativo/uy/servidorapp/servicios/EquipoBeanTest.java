package codigocreativo.uy.servidorapp.servicios;

import codigocreativo.uy.servidorapp.dtos.BajaEquipoDto;
import codigocreativo.uy.servidorapp.dtos.EquipoDto;
import codigocreativo.uy.servidorapp.dtos.dtomappers.BajaEquipoMapper;
import codigocreativo.uy.servidorapp.dtos.dtomappers.CycleAvoidingMappingContext;
import codigocreativo.uy.servidorapp.dtos.dtomappers.EquipoMapper;
import codigocreativo.uy.servidorapp.entidades.BajaEquipo;
import codigocreativo.uy.servidorapp.entidades.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EquipoBeanTest {

    @Mock
    private EntityManager em;

    @Mock
    private EquipoMapper equipoMapper;

    @Mock
    private BajaEquipoMapper bajaEquipoMapper;

    @InjectMocks
    private EquipoBean equipoBean;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        equipoBean = new EquipoBean(equipoMapper, bajaEquipoMapper);

        // Inyectar el EntityManager usando reflexión
        Field emField = EquipoBean.class.getDeclaredField("em");
        emField.setAccessible(true);
        emField.set(equipoBean, em);
    }

    @Test
    void testCrearEquipo() {
        EquipoDto equipoDto = new EquipoDto();
        Equipo equipoEntity = new Equipo();

        when(equipoMapper.toEntity(eq(equipoDto), any(CycleAvoidingMappingContext.class))).thenReturn(equipoEntity);

        equipoBean.crearEquipo(equipoDto);

        verify(em).persist(equipoEntity);
        verify(em).flush();
    }

    @Test
    void testModificarEquipo() {
        EquipoDto equipoDto = new EquipoDto();
        Equipo equipoEntity = new Equipo();

        when(equipoMapper.toEntity(eq(equipoDto), any(CycleAvoidingMappingContext.class))).thenReturn(equipoEntity);

        equipoBean.modificarEquipo(equipoDto);

        verify(em).merge(equipoEntity);
        verify(em).flush();
    }

    @Test
    void testEliminarEquipo() {
        BajaEquipoDto bajaEquipoDto = new BajaEquipoDto();
        bajaEquipoDto.setIdEquipo(new EquipoDto());
        bajaEquipoDto.getIdEquipo().setId(1L);
        BajaEquipo bajaEquipoEntity = new BajaEquipo();
        Equipo equipoEntity = new Equipo();

        when(bajaEquipoMapper.toEntity(eq(bajaEquipoDto), any(CycleAvoidingMappingContext.class))).thenReturn(bajaEquipoEntity);
        when(em.merge(any(BajaEquipo.class))).thenReturn(bajaEquipoEntity);
        when(em.find(Equipo.class, 1L)).thenReturn(equipoEntity);

        // Mockear TypedQuery correctamente
        TypedQuery<Equipo> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("UPDATE Equipo equipo SET equipo.estado = 'INACTIVO' WHERE equipo.id = :id")).thenReturn(mockedQuery);
        when(mockedQuery.setParameter("id", 1L)).thenReturn(mockedQuery);
        when(mockedQuery.executeUpdate()).thenReturn(1);

        equipoBean.eliminarEquipo(bajaEquipoDto);

        verify(em).merge(bajaEquipoEntity);
        verify(mockedQuery).setParameter("id", 1L);
        verify(mockedQuery).executeUpdate();
        verify(em).flush();
    }

    @Test
    void testObtenerEquiposFiltrado() {
        Map<String, String> filtros = new HashMap<>();
        filtros.put("nombre", "test");

        StringBuilder queryStr = new StringBuilder("SELECT e FROM Equipo e WHERE 1=1 AND LOWER(e.nombre) LIKE LOWER(:nombre)");
        TypedQuery<Equipo> query = mock(TypedQuery.class);

        when(em.createQuery(queryStr.toString(), Equipo.class)).thenReturn(query);
        when(query.setParameter("nombre", "%test%"))
                .thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(new Equipo()));
        when(equipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(Collections.singletonList(new EquipoDto()));

        List<EquipoDto> result = equipoBean.obtenerEquiposFiltrado(filtros);

        assertEquals(1, result.size());
        verify(query).setParameter("nombre", "%test%");
    }

    @Test
    void testObtenerEquipo() {
        Long id = 1L;
        Equipo equipoEntity = new Equipo();
        EquipoDto equipoDto = new EquipoDto();

        when(em.find(Equipo.class, id)).thenReturn(equipoEntity);
        when(equipoMapper.toDto(eq(equipoEntity), any(CycleAvoidingMappingContext.class))).thenReturn(equipoDto);

        EquipoDto result = equipoBean.obtenerEquipo(id);

        assertEquals(equipoDto, result);
        verify(em).find(Equipo.class, id);
    }

    @Test
    void testListarEquipos() {
        List<Equipo> equiposEntityList = Collections.singletonList(new Equipo());
        List<EquipoDto> equiposDtoList = Collections.singletonList(new EquipoDto());

        TypedQuery<Equipo> mockedQuery = mock(TypedQuery.class);
        when(em.createQuery("SELECT equipo FROM Equipo equipo", Equipo.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(equiposEntityList);
        when(equipoMapper.toDto(anyList(), any(CycleAvoidingMappingContext.class))).thenReturn(equiposDtoList);

        List<EquipoDto> result = equipoBean.listarEquipos();

        assertEquals(equiposDtoList, result);
        verify(em).createQuery("SELECT equipo FROM Equipo equipo", Equipo.class);
    }
}
